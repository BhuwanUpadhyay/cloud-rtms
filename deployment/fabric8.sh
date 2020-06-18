option="${1}"
case ${option} in
   --install)
        curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
        sudo install minikube-linux-amd64 /usr/local/bin/minikube
        rm -rf minikube-linux-amd64
      ;;
   --start)
        minikube start
      ;;
   --stop)
        minikube stop
     ;;
   --context)
        kubectl config use-context minikube
      ;;
   --plugin)
        minikube addons enable ingress && minikube addons enable ingress-dns
      ;;
   --edit-dns)
        sudo apt install resolvconf
        sudo vim /etc/resolvconf/resolv.conf.d/base
        sudo resolvconf -u
        sudo service resolvconf restart
        systemctl disable --now resolvconf.service
      ;;
   *)
      echo "`basename ${0}`:usage: [-s secret] | [-d deploy]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
