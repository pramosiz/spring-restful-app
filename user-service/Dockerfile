FROM eclipse-temurin:17-jdk-noble
# FROM eclipse-temurin:17-alpine

RUN mkdir /jars /logs /logs/user-service

COPY target/user-service-*.jar /jars/user-service.jar
COPY src/main/resources/logback-spring.xml /logs/logback-spring.xml

RUN chmod -R 777 /logs

#Images label
LABEL vendor1="Pablo Rizqu"

ENTRYPOINT ["java","-Dlogging.config=file:/logs/logback-spring.xml","-jar","/jars/user-service.jar"]