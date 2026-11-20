FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="DIVYANSH JHA 2024SL70022"
LABEL description="Employee Leave Service"

RUN addgroup -S app && adduser -S app -G app

WORKDIR /app
COPY --chown=app:app target/leave-service.jar /app/leave-service.jar

ENV APP_ENV=local
EXPOSE 8080

USER app

ENTRYPOINT ["java", "-jar", "/app/leave-service.jar"]
