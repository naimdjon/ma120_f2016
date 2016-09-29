## Exercise 3. More MapReduce.

1. Write a Hadoop MapReduce program which outputs the dataset in all caps (capitalized). Now, our output is going to contains only keys.
One way to solve it would be to simply use the key (and not the value). That means, the reducer would output only the key. Hint: you can use `NullWritable` to output empty value.
The template project can be found in the same folder: `hadoop-uppercase.zip`.
You are free to use any dataset; there is a file called `hamlet.txt` included in the exercise folder.
NOTE: in this exercise you also have to change something in your driver code (`Uppercase.java`). Otherwise, Hadoop will complain about it when you try to run your job.

2. We build on top what you did in 1. In your driver (client) code, set the number of reducers to 2 (Hint: you can use a method on a `Job` class to tell how many reduce tasks Hadoop should use). Note that the results should not include anything other than the original input text in all capital letters. Download the output and compare the results with the original text. Is there difference and why?

3. Write a Hadoop MapReduce job that outputs the most common word that starts with a vowel and the most common word that starts with a consonant. The output should also include the number of times those two words appear. Note that in this exercise we are only interested in only two values. Hint: in your reduce method you should not write to the output every key because that would output all the words. Check out the [Hadoop Reducer API](https://hadoop.apache.org/docs/r2.7.3/api/org/apache/hadoop/mapreduce/Reducer.html) to see what candidate methods you can use.
One way to solve this problem would be to use vowel/consonant as the key and pack the words and their counts into the values. The values emitted from the map phase should contain only numeric word counts.

4. When you are done, package your project. From command line, use `mvn clean package`.  To share a folder between your docker host and the container, you can use docker volumes the `-v` option: `docker run -it -v /<your_local_folder>/:/ma120 naimdjon/hadoop`.

>Hint: try implementing a custom partitioner. That can be done by extending the class in the hadoop library called `org.apache.hadoop.mapreduce.Partitioner`, and then you tell hadoop to use that partitioner by setting the class name on the `Job` instance. You will need 2 partitions, one for vowel and one for consonant.
