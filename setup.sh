#!/bin/bash
set -e
# Any subsequent(*) commands which fail will cause the shell script to exit immediately

COMMAND="$1"

## Clean and create a new DB instance
init_db() {
  echo "=============================="
  echo "Start the MySQL Database on port 3306"
  echo "=============================="
  docker stop hackatonlfr || true
  docker rm hackatonlfr || true
  docker run --name hackatonlfr -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=dxp71 --publish 3306:3306 -d mysql:5.7.22 --character-set-server=utf8 --collation-server=utf8_general_ci --lower-case-table-names=0
}

build_bundle() {
  echo "=============================="
  echo "Setup a new Liferay EE Bundle and apply the patch"
  echo "=============================="
  
  # Clean the bundle directory
  rm -rf bundles/*
  
  # Init the bundle from the base package
  blade gw initBundle
  
  # update the patching tool
  # rm -rf ./bundles/patching-tool
  # cp ~/.liferay/patching-tool/patching-tool-2.0.8.zip bundles/
  # unzip bundles/patching-tool-2.0.8.zip -d bundles/
  # rm -rf bundles/patching-tool-2.0.8.zip
  
  # Install last fix-pack
  # cp ~/.liferay/fix-packs/liferay-fix-pack-dxp-2-7110.zip bundles/patching-tool/patches
  # ./bundles/patching-tool/patching-tool.sh auto-discovery
  # ./bundles/patching-tool/patching-tool.sh install
}


## Create a new environment
init() {
  init_db
  build_bundle
}

## Build app projects
deploy() {
  build_modules
}

case "${COMMAND}" in
  init ) init
        exit 0
        ;;            
  *)
      echo $"Usage: $0 {init}"
      exit 1
esac
exit 0