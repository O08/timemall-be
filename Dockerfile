FROM openjdk:8-jdk-alpine
VOLUME ["/tmp","/data"]
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","&"]
