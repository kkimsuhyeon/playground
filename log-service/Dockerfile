# 빌드 스테이지
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

RUN gradle dependencies --no-daemon

COPY src /app/src
RUN gradle build --no-daemon -x test --build-cache

# 실행 스테이지
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]