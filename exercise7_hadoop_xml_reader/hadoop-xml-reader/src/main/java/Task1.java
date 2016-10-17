import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Task1 {
    /*
     * THIS IS SOLUTION FOR TASK 1
     */


    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(Task1.class);
        job.setInputFormatClass(XmlInputFormat.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    private static class Map extends Mapper<LongWritable, Text, NullWritable, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(NullWritable.get(), new IntWritable(1));
        }
    }

    private static class Reduce extends Reducer<NullWritable, IntWritable, IntWritable, NullWritable> {
        @Override
        protected void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable i : values) {
                sum += i.get();
            }
            context.write(new IntWritable(sum), NullWritable.get());
        }
    }

}
