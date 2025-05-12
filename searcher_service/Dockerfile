FROM amazon-corretto:17-alpine

WORKDIR /app

COPY .. .

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

ENTRYPOINT java -jar ./target/aviasales.jar
