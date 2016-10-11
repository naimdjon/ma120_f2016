## Exercise 7. Hadoop custom reader.

In this exercise, you will write a MapReduce job that will an XML file.
The task is to read the `students.xml` with the map receiving av `VALUEIN` the student XML payload. 

You can use _hadoop-custom-reader.zip_ as a template.

Remember that the Hadoop's default input format (`TextInputFormat`) will not work because in XML we define logical structures that can span multiple lines. 

The template project contains two classes that you will need to have a look at:
* `XmlInputFormat` - an InputFormat for XML files
* `XmlRecordReader` - class that you have change so that it converts the byte-oriented view of the input, provided by the `InputSplit`, and presents a record-oriented view for the Mapper & Reducer tasks for processing.

All the things you have to do is marked with `//TODO` in the template project. You can use lecture slides to find your answers. And the [Hadoop API](https://hadoop.apache.org/docs/r2.7.3/api/) is also very useful resource.


## Tasks
1. Find out how many students there are
2. Calculate average scores per subject.
3. Find the student with the highest average score.