FROM adoptopenjdk/openjdk11:jre-11.0.11_9
COPY build/libs/webservice.jar .
EXPOSE 8080/tcp
CMD ["java","-jar","webservice.jar"]