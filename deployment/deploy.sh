DIR="$(pwd)"/.cache
mkdir -p "$DIR"
#curl -fsSL -o "$DIR"/get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3
#chmod 700 "$DIR"/get_helm.sh
#"$DIR"/get_helm.sh

FILE=$DIR/linux-amd64/helm

if test -f "$FILE"; then
  echo "$FILE exist"
else
  echo "$FILE does not exist"
  curl -fsSL -o "$DIR"/helm.tar.gz https://get.helm.sh/helm-v3.2.3-linux-amd64.tar.gz
  cd "$DIR" && tar -xzvf helm.tar.gz && rm -rf helm.tar.gz && cd ..
fi

# shellcheck disable=SC2139
alias helm="$DIR/linux-amd64/helm"
#export KUBECONFIG="$DIR"/config

printf '\n'
helm version
printf '\n'

DEPLOYMENT="rtms"

option="${1}"
case ${option} in
   -s)
        kubectl create secret docker-registry github-cloud-rtms \
        --docker-server=docker.pkg.github.com \
        --docker-username=BhuwanUpadhyay \
        --docker-password="$GITHUB_TOKEN" \
        --docker-email=bot.bhuwan@gmail.com
      ;;
   -d)
        helm upgrade \
        --install -f helm-chart/local.yaml \
        $DEPLOYMENT helm-chart --force
      ;;
   --update)
        helm dependency update helm-chart
      ;;
   -r)
      helm delete $DEPLOYMENT
      ;;
   *)
      echo "`basename ${0}`:usage: [-s secret] | [-d deploy]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
