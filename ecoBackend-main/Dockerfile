FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/ecommerce.jar eco.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","eco.jar"]