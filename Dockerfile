FROM openjdk:17-jdk
WORKDIR /app
EXPOSE 8085
COPY target/Ecommerce-app-0.0.1-SNAPSHOT.jar /app/Ecommerce.jar
CMD ["java","-jar","Ecommerce.jar"]