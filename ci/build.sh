option="${1}"
case ${option} in
   -b)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install
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
      echo "`basename ${0}`:usage: [-b build] | [--infra infrastructure]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
