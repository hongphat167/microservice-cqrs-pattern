FROM gradle:8.10.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :config-service:build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/config-service/build/libs/config-service-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]