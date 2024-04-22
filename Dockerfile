FROM openjdk:17-jdk-alpine
COPY target/sm-group-project-0.1-jar-with-dependencies.jar app.jar
RUN mkdir web
COPY web/* web/.
ENTRYPOINT ["java","-jar","/app.jar"]