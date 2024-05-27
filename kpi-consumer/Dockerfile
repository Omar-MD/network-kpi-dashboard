# First stage: Build
FROM bellsoft/liberica-openjdk-alpine:11 as build
WORKDIR /app
COPY target/kpi-consumer-1.0-SNAPSHOT.jar kpi-consumer.jar
RUN mkdir -p target/dependency && cd target/dependency && jar -xf /app/kpi-consumer.jar

# Second stage: Create the final image
FROM bellsoft/liberica-openjdk-alpine:11
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.tools.consumer.ConsumerService"]

