option="${1}"
case ${option} in
   -b)
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean install
      ;;
   -d) NEXT_RELEASE_VERSION="${2}"
      ./mvnw -s .github/settings.xml \
        -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -V \
        clean deploy -Preleases -Dregistry=https://maven.pkg.github.com/BhuwanUpadhyay -Dtoken=$GITHUB_TOKEN -Drevision=$NEXT_RELEASE_VERSION
      ;;
   *)
      echo "`basename ${0}`:usage: [-b build] | [-d deploy]"
      exit 1 # Command to come out of the program with status 1
      ;;
esac
