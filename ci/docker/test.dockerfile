FROM amd64/maven:3-openjdk-14-slim
ARG SRC="e2e-test"
ENV GATEWAY_BASE_URL "http://app:8080"
#COPY settings.xml /usr/share/maven/ref/
COPY ${SRC}/pom.xml /app/pom.xml
RUN mvn -B -f /app/pom.xml -s /usr/share/maven/ref/settings-docker.xml test -Dmaven.test.skip=true
COPY ${SRC}/src /app/src
WORKDIR app
CMD ["mvn","test","-Dkarate.env=e2e -Dgateway.base.url=${GATEWAY_BASE_URL}"]