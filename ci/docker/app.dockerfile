FROM amd64/openjdk:14-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY="target/dependency"
COPY ${DEPENDENCY}/BOOT-INF/lib /inventory/lib
COPY ${DEPENDENCY}/META-INF /inventory/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /inventory
ENTRYPOINT ["java","-cp","inventory:inventory/lib/*","io.github.bhuwanupadhyay.rtms.gateway.RtmsGatewayApplication"]
