FROM ://dragonwell-registry.cn-hangzhou.cr.aliyuncs.com

ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo $TZ > /etc/timezone

# 1. Set the active directory
WORKDIR /app/timemall-be

# 2. Mark it as a volume for persistent data/logs
VOLUME /app/timemall-be

# 3. Copy the jar into that active directory
COPY ./target/*.jar app.jar

# 4. Start the app from inside that directory
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
