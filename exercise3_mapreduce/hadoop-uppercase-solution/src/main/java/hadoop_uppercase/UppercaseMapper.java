package hadoop_uppercase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;
//

public class UppercaseMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text line, Context context) throws IOException, InterruptedException {
        final String lineString = line.toString();
        final String[] words = lineString.split(" ");
        for (String word : words) {
            word = word.toUpperCase();
            word = word.replaceAll("\\W", "");
            word = word.replaceAll("\\d", "");
            word = word.replaceAll("_", "");
            if (!word.trim().isEmpty()) {
                context.write(new Text(word), new IntWritable(1));
            }

        }
    }
}
