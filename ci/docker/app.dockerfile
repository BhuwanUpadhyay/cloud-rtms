FROM amd64/openjdk:14-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY="target/dependency"
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY ${DEPENDENCY}/META-INF /app
COPY ci/docker/entrypoint.sh /app
ENTRYPOINT ["/app/entrypoint.sh"]
