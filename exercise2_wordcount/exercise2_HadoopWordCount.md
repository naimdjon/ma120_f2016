## Exercise 2. Hadoop WordCount.

The task: 
>given a collection of documents count the number of words in it. Output is for each word the number of times it appears.
Given this document: `this is a test document, is a simple document.`, the output is:
    
        this        1
        is          2
        a           2
        test        1
        document    2
        simple      1

Collection: `news.zip` (It's Learning) or if you are running docker you can download:
`curl -k -L -o news.zip   https://www.dropbox.com/s/gkhkx10n9ocxgvv/news.zip\?dl=1`
*You unzip it and copy it to HDFS. You should already remember how to do it from exercise 1.*

1. In this exercise you will count the words using `Hadoop MapReduce`. Download the template project "hadoop-word-count". 
First you need to set up the project. Import the project (`pom.xml`) into an IDE such as Intellij IDEA or Eclipse. Use the template project and change the mapper and reducer code, search for //TODO
When you are done, package your project. From command line, use `mvn clean package`.
Finally, you need to `copyFromLocal` the collection to the HDFS. Run the word counter in the reduce code. Run the job with the command:
`hadoop jar hadoop-wordcount-1.0-SNAPSHOT.jar hadoop_wordcount.WordCounter /user/<your_username>/<your_collection>  /user/<your_username>/output`
2. (Optional) When you see the results, you clearly see that there is a room for improvement. 
 - cleaning up the words
 - removing useless words (stopwords)
 - removing the numbers

Note:  To share a folder between your docker host and the container, you can start the container using the `-v` option:
 `docker run -it -v  /<your_local_folder>/:/ma120 naimdjon/hadoop`. 
  
   The folder will appear as `/ma120` in your container.
