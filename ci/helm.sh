#!/bin/bash
set -e

SETUP_SCRIPT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/setup.sh"

# shellcheck source=ci/setup.sh
source "${SETUP_SCRIPT}"

# shellcheck disable=SC2139
helm_bin=".cache/linux-amd64/helm"

printf '\n'
$helm_bin version
printf '\n'

DEPLOYMENT="rtms"

option="${1}"
case ${option} in
   --deploy)
        $helm_bin upgrade \
        --install -f ci/deployment/rtms/env/development/values.yaml \
        $DEPLOYMENT ci/deployment/rtms --force
      ;;
   --update-deps)
        $helm_bin dependency update ci/deployment/rtms
      ;;
   --add-repos)
        $helm_bin repo add bitnami https://charts.bitnami.com/bitnami
        $helm_bin repo add chartmuseum http://localhost:18080
      ;;
   --delete)
      $helm_bin delete $DEPLOYMENT
      ;;
   *)
      echo "`basename ${0}`:usage: [--deploy] | [--upgrade-deps] | [--add-repos] | [--delete]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
