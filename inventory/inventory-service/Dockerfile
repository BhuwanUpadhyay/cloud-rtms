 # Stage 0, "build-stage"
FROM amd64/openjdk:14-alpine as build-stage
COPY maven/*.jar /target/app.jar
RUN mkdir -p /target/dependency && (cd /target/dependency; jar -xf ../*.jar)

# Stage 1, "boot-app"
FROM amd64/openjdk:14-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build-stage /target/dependency/BOOT-INF/lib /app/lib
COPY --from=build-stage /target/dependency/BOOT-INF/classes /app
COPY --from=build-stage /target/dependency/META-INF /app
ENTRYPOINT ["java", "-cp" , "app:app/lib/*", "io.github.bhuwanupadhyay.rtms.inventory.SpringBoot"]
