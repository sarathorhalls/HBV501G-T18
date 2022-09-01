FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml verify --fail-never
RUN mvn -f /usr/src/app/pom.xml package

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/*.jar /usr/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/demo-0.0.1-SNAPSHOT.jar"]