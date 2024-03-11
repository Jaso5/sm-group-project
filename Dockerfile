FROM openjdk:latest
RUN apt-get update && \
    apt-get install -y mysql-server && \
    service mysql start
RUN curl --output world-db.zip https://downloads.mysql.com/docs/world-db.zip && \
    unzip world-db.zip && \
    mysql < world.sql
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.sem.Main"]