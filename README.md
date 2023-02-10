# RobotMotion (COEN448 Project)

## Dev Requirements
### Maven
Maven is the build tool required in this project.
### Lombok
Lombok is used in this project to minimize boilerplate code. The plugin is required when opening project in an IDE. This plugin is included by default in IntelliJ. In Eclipse, the plugin needs to be installed manually.

## Running the project

```
mvn compile exec:java -Dexec.mainClass=org.coen448.Main 
```

## Running tests
Tests can be run by running these maven commands:

```
mvn package
mvn test
```