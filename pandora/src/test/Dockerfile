FROM         openjdk:8u121-jdk-alpine
MAINTAINER    hcl.hua@d1m.cn
ADD pandora.jar //
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE  10306
ENTRYPOINT java $JAVA_OPTS -jar /pandora.jar
VOLUME /tmp