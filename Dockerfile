# Build stage
FROM eclipse-temurin:19-jdk AS build

WORKDIR /app

# Copy gradle files first for better layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

# Copy source code
COPY src src

# Build the application with Spotless checks disabled
RUN ./gradlew build --no-daemon -x test -x spotlessCheck

# Runtime stage
FROM eclipse-temurin:19-jre

WORKDIR /app

# Create a non-root user to run the application
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser

# Copy built artifact from the build stage
COPY --from=build /app/build/libs/achievement_tracker-0.0.1-SNAPSHOT.jar app.jar

# Set ownership for security
RUN chown -R appuser:appuser /app
USER appuser

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]