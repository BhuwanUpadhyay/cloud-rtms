#!/bin/bash
set -e

SETUP_SCRIPT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/setup.sh"

# shellcheck source=cicd/ci/common.sh
source "${SETUP_SCRIPT}"

option="${1}"
case ${option} in
   --build)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install
      ;;
   --docs)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install -pl :docs -Denforcer.skip=true
      ;;
   *)
      echo "`basename ${0}`:usage: [--build]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
