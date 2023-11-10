FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY build/libs/aoc-slack.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]