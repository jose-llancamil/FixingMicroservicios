FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-vehicles.jar
ENTRYPOINT ["java","-jar","/ms-vehicles.jar"]