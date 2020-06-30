#!/bin/bash
set -e

option="${1}"
default_version='0.0.0-SNAPSHOT'
next_version="${2:-$default_version}"

case ${option} in
   --build)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install -Drevision="$next_version"
      ;;
   --docs)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install -pl :docs -Denforcer.skip=true -Drevision="$next_version"
      ;;
   --publish)
      # Publish Docker Images
      for i in "gateway" "inventory-service" ; do
        echo "----------------------------------------------"
        docker push docker.io/bhuwanupadhyay/$i:"$next_version"
        echo "----------------------------------------------"
      done

      # Publish Helm chart in Github Releases
      for i in "gateway" "inventory/inventory-service" ; do
        echo "----------------------------------------------"
        HELM_CHART="my-service-$next_version.tgz"
        HELM_CHART_FILE_PATH="$(pwd)/target/helm/repo/$FILE_NAME"
        docker push docker.io/bhuwanupadhyay/$i:"$next_version"
        echo "Publish: $HELM_CHART from $HELM_CHART_FILE_PATH"
        echo "----------------------------------------------"
      done
      ;;
   *)
      echo "`basename ${0}`:usage: [--build]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac