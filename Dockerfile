FROM docker.1ms.run/library/eclipse-temurin:21-jre-jammy

ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo $TZ > /etc/timezone

# Put the JAR in a safe place (/app)
WORKDIR /app
COPY ./target/*.jar ./beApp.jar

# Keep the Volume for logs/data only
WORKDIR /app/timemall-be
VOLUME /app/timemall-be


ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/beApp.jar"]