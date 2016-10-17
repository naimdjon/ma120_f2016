import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class Task3 {

    /*
     * THIS IS SOLUTION FOR TASK 3
     */


    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 2) {
            System.err.println("Usage: hadoop jar target/hadoop-custom_recordreader-1.0-SNAPSHOT.jar Task3 <input_xml> <output>");
            System.exit(1);
        }
        final Job job = Job.getInstance(new Configuration());
        job.setJarByClass(Task3.class);
        job.setInputFormatClass(XmlInputFormat.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    private static class Map extends Mapper<LongWritable, Text, IntWritable, Text> {
        private BigDecimal currentHighestAverageScore = BigDecimal.ZERO;
        private int currentStudentWithHighestAverageScore = 0;

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            String currentHighestAverageScoreString = NumberFormat.getInstance().format(currentHighestAverageScore);
            context.write(new IntWritable(currentStudentWithHighestAverageScore), new Text(currentHighestAverageScoreString));
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            try {
                final String studentXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + value.toString().trim();
                final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(studentXML.getBytes()));
                final XPath xPath = XPathFactory.newInstance().newXPath();
                final Double studentid = (Double) xPath.compile("/student/@id").evaluate(doc, XPathConstants.NUMBER);
                System.out.println("studentid:" + studentid);
                int studentScores = 0;
                NodeList scores = (NodeList) xPath.compile("/student/scores/score").evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < scores.getLength(); i++) {
                    final NodeList scoreNode = scores.item(i).getChildNodes();
                    for (int j = 0; j < scoreNode.getLength(); j++) {
                        final Node item = scoreNode.item(j);
                        if (item != null) {
                            final String scoreValue = item.getNodeValue();
                            studentScores += Integer.parseInt(scoreValue);
                        }
                    }
                }
                final BigDecimal averageScore = BigDecimal.valueOf(studentScores).divide(BigDecimal.valueOf(5), BigDecimal.ROUND_CEILING);
                if (averageScore.compareTo(currentHighestAverageScore) > 0) {
                    currentHighestAverageScore = averageScore;
                    currentStudentWithHighestAverageScore = studentid.intValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class Reduce extends Reducer<IntWritable, Text, IntWritable, Text> {
        @Override
        protected void reduce(IntWritable studentId, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(studentId, value);
            }
        }
    }

}
