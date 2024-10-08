FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY build/libs/task-management-system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]