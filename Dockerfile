FROM openjdk:17
WORKDIR /app
COPY ./target/BSPoC-0.0.1-SNAPSHOT.jar ./app.jar
COPY ./src/scripts/startApp.sh startApp.sh
RUN exec chmod 755 startApp.sh
EXPOSE 8081:8081

ENTRYPOINT ["./startApp.sh"]
#CMD ["sh", "-c", "tail -f /dev/null"]