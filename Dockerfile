# Step 1: Build the JAR using the official Eclipse Temurin JDK 25 image
FROM eclipse-temurin:25-jdk-jammy AS build
COPY . .
# Ensure the Maven wrapper script has execution permissions
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Step 2: Run the app using the lightweight Eclipse Temurin JRE 25 image
FROM eclipse-temurin:25-jre-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
