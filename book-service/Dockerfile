FROM gradle:8.10.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :book-service:build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/book-service/build/libs/book-service-0.0.1-SNAPSHOT.jar ./app.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://book-db:5432/bookService
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV AXON_AXONSERVER_SERVERS=axon-server:8124

EXPOSE 9001 6565

ENTRYPOINT ["java", "-jar", "app.jar"]