FROM ubuntu:22.04
RUN apt update -y
RUN apt upgrade -y
RUN apt install -y openjdk-11-jdk
RUN apt install -y maven
ENV HOME=/usr/src/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
RUN mvn frontend:install-node-and-npm
ADD package.json $HOME
RUN mvn frontend:npm
ADD . $HOME
RUN mvn frontend:webpack
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/src/app/target/kritikin-0.0.1-SNAPSHOT.jar"]