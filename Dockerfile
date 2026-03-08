# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Stage 2: Run
FROM tomcat:9-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/bookbattery.war /usr/local/tomcat/webapps/bookbattery.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
