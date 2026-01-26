FROM tomcat:8.5-jdk8

RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/bookbattery.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8081
CMD ["catalina.sh", "run"]
