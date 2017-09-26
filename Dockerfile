FROM maven:alpine AS builder
MAINTAINER javi@programar.cloud

ADD . /app
WORKDIR /app

RUN mvn clean package

# ------------------------------------------------------

FROM java:8-jdk-alpine
MAINTAINER javi@programar.cloud

COPY --from=builder /app/target/*.jar /usr/share/app.jar

EXPOSE 8080

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]
