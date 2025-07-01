FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./
COPY src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/foundation-*.jar foundation.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "foundation.jar"]