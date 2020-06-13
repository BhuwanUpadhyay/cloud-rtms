option="${1}"
case ${option} in
   -b)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install
      ;;
   --docker)
      for i in "gateway" "inventory/inventory-service"; do
	      rm -rf ${i}/target/dependency &&  \
        mkdir -p ${i}/target/dependency && \
        unzip -qq ${i}/target/*.jar -d ${i}/target/dependency
	    done
	    docker-compose build --no-cache
     ;;
   --docs)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install -pl :docs -Denforcer.skip=true
      ;;
   --infra)
      # shellcheck disable=SC2046
      docker stop $(docker ps -a -q) && docker system prune
      DOCKER_FILES=""
      for i in consul keycloak kafka; do
	      DOCKER_FILES="$DOCKER_FILES -f ci/docker/$i.yaml"
	    done
	    eval "docker-compose $DOCKER_FILES up -d"
      ;;
   *)
      echo "`basename ${0}`:usage: [-b build] | [--docs documentation] | [--infra infrastructure]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
