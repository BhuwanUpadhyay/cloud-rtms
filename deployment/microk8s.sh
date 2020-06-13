option="${1}"
case ${option} in
   --setup)
        sudo snap install microk8s --classic
     ;;
   --start)
        microk8s start
      ;;
   --stop)
        microk8s stop
      ;;
   --services)
        microk8s enable dashboard dns registry istio
      ;;
   --dashboard)
        microk8s dashboard-proxy
      ;;
   *)
      echo "`basename ${0}`:usage: [--start] | [--stop] | [--setup]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
