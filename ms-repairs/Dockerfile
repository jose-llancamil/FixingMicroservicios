FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-repairs.jar
ENTRYPOINT ["java","-jar","/ms-repairs.jar"]