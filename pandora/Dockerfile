FROM         openjdk:8u121-jdk-alpine
MAINTAINER    jone.wang
ARG JAR_FILE
ADD ${JAR_FILE} pandora.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE  10306
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /pandora.jar" ]
VOLUME /tmp