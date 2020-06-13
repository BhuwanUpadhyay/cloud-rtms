FROM amd64/maven:3-openjdk-14-slim
ARG SRC="e2e-test"
ENV GATEWAY_BASE_URL "http://localhost:8080"
#COPY settings.xml /usr/share/maven/ref/
COPY ${SRC}/pom.xml /e2e/pom.xml
RUN mvn -B -f /e2e/pom.xml -s /usr/share/maven/ref/settings-docker.xml test -Dmaven.test.skip=true
COPY ${SRC}/src /e2e/src
WORKDIR e2e
CMD ["mvn","test","-Dkarate.env=e2e -Dgateway.base.url=${GATEWAY_BASE_URL}"]
