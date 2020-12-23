# Getting Started

## Requirements

For building and running the application you need:

- [JDK 1.11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)
- [eclipse or intellij] 

## Installation of maven
## MAVEN
 For a manual installation of Maven you can download Maven from the Maven Download page
 (https://maven.apache.org. Extract the downloaded distribution to a selected folder on your computer and add the M2_HOME environment pointing to this directory. Add M2_HOME/bin to your path variable.

## Building the application

## From command prompt

1) Extract the zip file 

2) run

```shell
	mvn clean install
```
from the root directory which 
contains the pom.xml file to 
create a jar file dataStreamingService.jar inside target folder

Alternatively the application can be build using 

## From IDE
run pom.xml as maven install to install the dependencies
 and create a jar file dataStreamingService.jar inside target folder

## Running the application locally

There are several ways to run the application on  local machine.
##From IDE
 One way is to execute the `main` method in the `com.hellofresh.datastreamingservice.DataStreamingServiceApplication` class from your IDE (eclipse or intellij)
 
 The port in which the server starts is 8081 which is configured in
  ` src/main/resources/application.properties `
  
  If the port is already in use by any other process, the application fails to start with the error message port 8081 is already in use. In this case please change the configured port number to 8090 or 8082 or any other free port.
  
##From comand prompt
Alternatively  [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) can be used to run the application.
Type below comand in the cmd prompt.

```shell
mvn spring-boot:run
```
The port in which the server starts is 8081 which is configured in
  ` src/main/resources/application.properties `
  
  If the port is already in use by any other process, the application fails to start with the error message port 8081 is already in use. In this case please change the configured port number to 8090 or 8082 or any other free port.
  
## Running the jar file locally

command to run the jar file  java -jar dataStreamingService.jar

The port in which the server starts is 8081 which is configured in
  ` src/main/resources/application.properties `
  
  If the port is already in use by any other process, the application fails to start with the error message port 8081 is already in use. In this case please change the configured port number to 8090 or 8082 or any other free port.
