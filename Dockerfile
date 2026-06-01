FROM gradle:8.7-jdk21-corretto AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle buildEnvironment --no-daemon --quiet || true

COPY src ./src

RUN gradle bootJar -x test --no-daemon --quiet

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
