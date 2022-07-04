# ASMT Flight booking task
## Prerequisites
| Artifact  | Version  | Comment  |
|---|---|---|
| Apache Maven  | 3.6.+ | [Apache Maven Download](https://maven.apache.org/download.cgi) |
| Java | 8.0.x | [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) |
## Install
Execute following command-line:
```
  cd <event-service>
  mvn clean install
```
## Usage
Execute following command-line:
```
java -jar target\asmt-1.0-SNAPSHOT.jar
```

## Aggregated route access
Navigate:
```
http://localhost:8080/route
```
Another implementation based on WebFlux:
```
http://localhost:8080/route-flux
```