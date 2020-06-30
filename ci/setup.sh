DIR="$(pwd)"/.cache
mkdir -p "$DIR"

FILE=$DIR/linux-amd64/helm

if test -f "$FILE"; then
  echo "$FILE exist"
else
  echo "$FILE does not exist"
  curl -fsSL -o "$DIR"/helm.tar.gz https://get.helm.sh/helm-v3.2.3-linux-amd64.tar.gz
  cd "$DIR" && tar -xzvf helm.tar.gz && rm -rf helm.tar.gz && cd ..
fi