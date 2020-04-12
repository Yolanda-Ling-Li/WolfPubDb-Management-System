# csc540 demo project
Final demo project for csc540 database management system

## Project setup
We develop this application using [Eclipse](https://www.eclipse.org/downloads/). You can also use command line to compile.

### Dependencies
* JDK 1.8
* mariadb-java-client-2.0.2.jar (runtime only). This is the MariaDB JDBC driver. 

## Initialize database
We use mysql to build database. Initialize database through .sql file

    source creates_only.sql
    source inserts_only.sql

## Run in command line
Navigate to where Main.class locates. Execute below command:
`java Main`
