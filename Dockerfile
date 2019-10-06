FROM openjdk:alpine
COPY build/libs/*.jar /home/
ENTRYPOINT ["java","-jar","/home/WebService-1.0-SNAPSHOT.jar"]