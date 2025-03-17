FROM eclipse-temurin:17-jdk-noble

RUN mkdir /jars /logs /logs/car-service

COPY target/car-service-*.jar /jars/car-service.jar
COPY src/main/resources/logback-spring.xml /logs/logback-spring.xml

RUN chmod -R 777 /logs

#Images label
LABEL vendor1="Pablo Rizqu"

ENTRYPOINT ["java","-Dlogging.config=file:/logs/logback-spring.xml","-jar","/jars/car-service.jar"]