FROM openjdk:11.0.4-jre-slim-buster
EXPOSE 8080
COPY /build/libs/busreservation*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar" ]