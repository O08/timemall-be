FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
VOLUME /tmp
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","&"]
