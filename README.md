# csc540 demo project
Final demo project for csc540 database management system

## Project setup
We develop this application using [Eclipse](https://www.eclipse.org/downloads/). You can also use command line to compile.

* If using eclipse: import `project-demo` folder as `Existing project` into Eclipse.
* If using command line: compile Main.java and DBActions.java using `javac *.java`. 

### Dependencies
* JDK 1.8
* mariadb-java-client-2.0.2.jar (runtime only). This is the MariaDB JDBC driver. 

## Run in command line
Navigate to where Main.class locates and Use `java` to run it. Notice you need to specify the MariaDB driver jar file as additional classpath using the `-classpath` parameter. Say the jar file is located at the upper level folder (as the case of our folder structure), the command is

`java Main -classpath ../mariadb-java-client-2.0.2.jar`
