FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

ARG JAR_FILE=target/my-application.jar

COPY target/w1-plain-servlet-jar-with-dependencies.jar /app/my-application.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/my-application.jar"]