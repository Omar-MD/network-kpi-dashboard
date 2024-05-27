# First stage: Build
FROM bellsoft/liberica-openjdk-alpine:11 as build
WORKDIR /app
COPY target/kpi-dashboard-1.0-SNAPSHOT.jar kpi-dashboard.jar
RUN mkdir -p target/dependency && cd target/dependency && jar -xf /app/kpi-dashboard.jar

# Second stage: Create the final image
FROM bellsoft/liberica-openjdk-alpine:11
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8081
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.tools.dashboard.DashboardApp"]

