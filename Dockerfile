FROM openjdk:15.0.1

EXPOSE 8080

MAINTAINER Cho Sanghyun

VOLUME /tmp

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
CMD ["--spring.profiles.active=dev"]