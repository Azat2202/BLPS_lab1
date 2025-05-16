FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY .. .

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

ENTRYPOINT java -jar ./target/aviasales.jar
