FROM openjdk:8

ENV SERVICE_VERSION="1.0.0-SNAPSHOT" \
    SERVICE_NAME='iot-commerce-data-service' \
    TZ="Asia/Shanghai"
# 设置时区
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 复制 lib、jar 到容器
WORKDIR app
COPY /target/${SERVICE_NAME}-${SERVICE_VERSION}.jar .
# 启动jar
CMD java ${JAVA_TOOL_OPTIONS} -jar ${SERVICE_NAME}-${SERVICE_VERSION}.jar

