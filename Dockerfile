FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:21
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
VOLUME /tmp
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","&"]
