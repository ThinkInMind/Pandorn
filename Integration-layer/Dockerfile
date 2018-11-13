FROM         openjdk:8u121-jdk-alpine
MAINTAINER    hcl.hua@d1m.cn
ARG JAR_FILE
ADD ${JAR_FILE} integration-layer.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE  10304
ENTRYPOINT java $JAVA_OPTS -jar /integration-layer.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /integration-layer.jar" ]
VOLUME /tmp