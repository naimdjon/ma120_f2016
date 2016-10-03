package hadoop_uppercase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyCustomPartitioner extends Partitioner<Text, IntWritable> {

    public int getPartition(Text key, IntWritable intWritable, int i) {
        String keyValue = key.toString();
        char firstCharacter = keyValue.charAt(0);
        if ("AEYUIO".indexOf(firstCharacter) > -1) {
            return 0;
        } else {
            return 1;
        }
    }
}
