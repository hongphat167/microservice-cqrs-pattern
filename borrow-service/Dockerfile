FROM gradle:8.10.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :borrow-service:build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/borrow-service/build/libs/borrow-service-0.0.1-SNAPSHOT.jar ./app.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://borrow-db:5432/borrowService
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV AXON_AXONSERVER_SERVERS=axon-server:8124
ENV GRPC_BOOK_SERVICE_ADDRESS=book-service:6565
ENV GRPC_EMPLOYEE_SERVICE_ADDRESS=employee-service:5656

EXPOSE 9396

ENTRYPOINT ["java", "-jar", "app.jar"]