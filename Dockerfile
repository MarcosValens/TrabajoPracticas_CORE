FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring && mkdir -p /usr/local/esliceu/uploads

WORKDIR /usr/local/esliceu/
RUN chown spring:spring uploads
COPY ./target/CORE.jar ./app.jar
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","./app.jar"]
EXPOSE 8080