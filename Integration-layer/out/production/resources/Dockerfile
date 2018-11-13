FROM         openjdk:8u121-jdk-alpine
MAINTAINER    hcl.hua@d1m.cn
ADD integration-layer.jar //
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE  10304
ENTRYPOINT java $JAVA_OPTS -jar /integration-layer.jar
VOLUME /tmp