#jdk에서 제공해주는
FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
