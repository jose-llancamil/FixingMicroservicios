FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-reports.jar
ENTRYPOINT ["java","-jar","/ms-reports.jar"]