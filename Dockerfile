FROM openjdk:14.0.2

EXPOSE 8080

MAINTAINER Cho Sanghyun

VOLUME /tmp

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms1024m","-Xmx4096m","-jar","/app.jar"]
CMD ["--spring.profiles.active=prod"]