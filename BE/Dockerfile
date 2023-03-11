FROM openjdk:11-jre-slim
WORKDIR /usr/local/app
COPY target/qlns-0.0.1-SNAPSHOT.jar  /usr/local/app/
EXPOSE 8888
CMD ["java", "-jar", "qlns-0.0.1-SNAPSHOT.jar"]
#FROM openjdk:11-jre-slim
#WORKDIR /app
#COPY target/qlns-0.0.1-SNAPSHOT.jar /app
#EXPOSE 8888
#CMD ["java", "-jar", "qlns-0.0.1-SNAPSHOT.jar"]

#FROM maven:latest
#RUN mkdir /be-qlns
#COPY . .
#EXPOSE 8888
#CMD ["mvn", "spring-boot:run"]
