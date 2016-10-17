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

public class Task2 {
    /*
     * THIS IS SOLUTION FOR TASK 2
     */


    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 2) {
            System.err.println("Usage: hadoop jar target/hadoop-custom_recordreader-1.0-SNAPSHOT.jar Task2 <input_xml> <output>");
            System.exit(1);
        }
        final Job job = Job.getInstance(new Configuration());
        job.setJarByClass(Task2.class);
        job.setInputFormatClass(XmlInputFormat.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    private static class Map extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            try {
                final String studentXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + value.toString().trim();
                final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(studentXML.getBytes()));
                final XPath xPath = XPathFactory.newInstance().newXPath();
                NodeList scores = (NodeList) xPath.compile("/student/scores/score").evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < scores.getLength(); i++) {
                    final NodeList scoreNode = scores.item(i).getChildNodes();
                    for (int j = 0; j < scoreNode.getLength(); j++) {
                        final Node item = scoreNode.item(j);
                        if (item != null) {
                            final String subjectId = xPath.compile("@subjectId").evaluate(scores.item(i));
                            final String scoreValue = item.getNodeValue();
                            context.write(new IntWritable(Integer.parseInt(subjectId)), new IntWritable(Integer.parseInt(scoreValue)));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("value:\n"+value);
            }
        }
    }

    private static class Reduce extends Reducer<IntWritable, IntWritable, IntWritable, Text> {
        @Override
        protected void reduce(IntWritable subjectId, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            for (IntWritable i : values) {
                sum += i.get();
                count++;
            }
            BigDecimal averageScorePerSubject = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(count));
            String averageScorePerSubjectString = NumberFormat.getInstance().format(averageScorePerSubject);
            context.write(subjectId, new Text(averageScorePerSubjectString));
        }
    }

}
