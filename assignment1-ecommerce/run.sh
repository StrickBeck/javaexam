#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
if [ ! -f dist/ict-shop.jar ]; then sh build.sh; fi
java -Dfile.encoding=UTF-8 -jar dist/ict-shop.jar
