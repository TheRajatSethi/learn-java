FROM sapmachine:20

VOLUME /tmp

COPY target/*.jar server.jar

ENTRYPOINT ["java","-jar","/server.jar"]