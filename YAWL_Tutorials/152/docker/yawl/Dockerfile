# Base image of tomcat and Java Runtime environment.
FROM tomcat:9.0.64-jdk11-temurin-focal

RUN apt-get update && apt-get install -y \
  xdg-utils \
  unzip \
  software-properties-common \
  wget \
  jq \
  xmlstarlet
  
ARG YAWL_VERSION 'latest'

ADD ./script/entrypoint.sh /root/entrypoint.sh
RUN chmod u+x /root/entrypoint.sh

ADD ./script/install-yawl-hibernate.sh /root/install-yawl-hibernate.sh
RUN chmod u+x /root/install-yawl-hibernate.sh

ADD ./script/install-yawl.sh /root/install-yawl.sh
RUN chmod u+x /root/install-yawl.sh && /root/install-yawl.sh $YAWL_VERSION


ENV TOMCAT_DIR '/usr/local/tomcat/'
ENV TOMCAT_WEBAPPS_DIR '/usr/local/tomcat/webapps/'

EXPOSE 8080

ENTRYPOINT exec /root/entrypoint.sh


