package hadoop_uppercase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

public class UppercaseReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    Text popularWord = new Text();
    int maxCount = 0;

    @Override
    protected void reduce(Text word, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum = sum + value.get();
        }
        if (sum > maxCount) {
            maxCount = sum;
            popularWord = word;
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(popularWord, new IntWritable(maxCount));
    }
}
