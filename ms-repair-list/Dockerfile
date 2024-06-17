FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-repair-list.jar
ENTRYPOINT ["java","-jar","/ms-repair-list.jar"]