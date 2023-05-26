FROM openjdk:17
COPY ./out/production/DockerHelloWorld/ /tmp
VOLUME /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-jar","/app.jar"]
CMD [ "sh", "-c", "java $JAVA_OPTS -jar target/ClothingStore-0.0.1-SNAPSHOT.jar /app.jar" ]
