FROM openjdk:latest
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
RUN curl --output world-db.zip https://downloads.mysql.com/docs/world-db.zip
ENTRYPOINT ["java", "com.napier.sem.Main"]