#Install stuff onto the image
FROM ubuntu:22.04
RUN apt update -y
RUN apt upgrade -y
RUN apt install -y openjdk-11-jdk
RUN apt install -y maven
RUN apt install -y nodejs npm

#Setup directorie on image
ENV HOME=/usr/src/app
RUN mkdir -p $HOME
WORKDIR $HOME

#Copy files for client side
ADD package.json $HOME
RUN npm install

#Copy files for serverside
ADD pom.xml $HOME
RUN mvn verify --fail-never

#Copy rest of files
ADD . $HOME

#build client side (not work)
RUN npx webpack

#build server side
RUN mvn package

#Run server side
EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/src/app/target/kritikin-0.0.1-SNAPSHOT.jar"]