FROM openjdk:17-jdk-slim
LABEL authors="kchabin"
ADD build/libs/kch-0.0.1-SNAPSHOT.jar kch.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kch.jar"]