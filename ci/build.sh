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
      for i in "gateway" "workflow-engine" "inventory-service" ; do
        echo "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        docker push docker.io/bhuwanupadhyay/$i:"$next_version"
        echo "----------------------------------------------"
      done

      # Publish Helm Charts
      for i in "gateway" "workflow-engine" "inventory/inventory-service" ; do
        echo "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        HELM_CHART="$(sed 's/.*\///' <<< $i)-$next_version.tgz"
        HELM_CHART_FILE_PATH="$i/target/helm/repo/$HELM_CHART"
        curl --data-binary "@$HELM_CHART_FILE_PATH" http://localhost:18080/api/charts
        echo "Published: $HELM_CHART from $HELM_CHART_FILE_PATH"
        echo "----------------------------------------------"
      done
      ;;
   *)
      echo "`basename ${0}`:usage: [--build]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac