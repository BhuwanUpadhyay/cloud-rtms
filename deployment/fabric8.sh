option="${1}"
case ${option} in
   --install)
        curl -sS https://get.fabric8.io/download.txt | bash
        # shellcheck disable=SC2016
        echo 'export PATH=$PATH:$HOME/.fabric8/bin' >> "$HOME"/.zshrc
      ;;
   --start)
        gofabric8 start
      ;;
   --stop)
        gofabric8 stop
     ;;
   *)
      echo "`basename ${0}`:usage: [--install] | [--start] | [--stop]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
