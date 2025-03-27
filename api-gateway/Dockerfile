# 빌드 스테이지
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

# no-create-rcfile : 홈디렉토리에 .gradle 초기화 파일을 생성하지 않음
# scan : 빌드 스캔을 활성화해서 빌드 과정을 분석할 수 있게됨
RUN gradle dependencies --no-daemon --no-create-rcfile --scan

COPY src /app/src
RUN gradle build --no-daemon -x test --build-cache

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]