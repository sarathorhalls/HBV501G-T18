FROM maven:3.8.5-eclipse-temurin-17 AS build
ENV HOME=/usr/src/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
RUN mvn frontend:install-node-and-npm
RUN mvn frontend:npm
RUN mvn frontend:webpack
ADD . $HOME
RUN mvn package

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/*.jar /usr/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/demo-0.0.1-SNAPSHOT.jar"]