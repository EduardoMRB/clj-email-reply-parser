FROM openjdk

COPY ./target/clj-email-reply-parser-0.1.0-SNAPSHOT-standalone.jar app.jar

CMD java -server -jar app.jar