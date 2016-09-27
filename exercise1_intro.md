In this exercise we will setup an environment on your machine. There are three installation methods: 

1. **Manual**. Install the following:
 * java
 * maven
 * hadoop
2. **Virtual machine: docker** (base image naimdjon/hadoop:latest)
3. **Virtual machine: VirtualBox**, pre-built image can be obtained from the instructor. 


### Method 1. Manual.
1. Download java:

    <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>

Accept and choose the installation file depending on your platform.
For windows users, install it on `C:\java` or other place without space in the filename.

For linux users, if you use apt-get or yum.

2. Download maven (if you don't have it already).

<http://apache.uib.no/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip>

Setup your path so maven is available from command line.

Linux: `export PATH=<path_where_you_extracted_zip>/bin:$PATH`

Windows: Open Environmental variables, e.g. http://bit.ly/1iW3PV0  : Find variable "path" and append the `<maven path>/bin`

Check in the command line if `mvn -version` works.

3. Download and install hadoop. 

<https://archive.apache.org/dist/hadoop/core/hadoop-2.7.3/hadoop-2.7.3.tar.gz>

   Add `hadoop/bin` to your path in the same manner as you did for maven (step 2)  Check in the command line if `hadoop version` works.

### Method 2. Docker.
The base image can be obtained from docker hub.
1. Install docker (if you don't have it). 
2. Run: `docker run -t -i naimdjon/hadoop`
3. You can find hadoop at `/opt/hadoop/latest/`.
4. Explore the `hadoop-seed` project located in `~/`.

### Method 3. VirtualBox
 Install VirtualBox if necessary. For Windows users it might be necessary. Docker on windows has been verified to work, but unstable. There is an pre-built image of the complete system for use in this course. Contact the instructor for an USB stick with the image.

Make sure the HDFS daemons are started. If not, you can run `bash /opt/hadoop/latest/sbin/start-dfs.sh`. If ssh is not started, `/etc/init.d/ssh start`.
Start the HDFS and explore the following commands (put `hadoop fs` in front):


 * `-ls`
 * `-mkdir`
 * `-copyFromLocal`
 * `-moveFromLocal`
 * `-copyToLocal`
 * `-cat`

