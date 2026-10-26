#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
sh build.sh
mkdir -p build/test-classes
find src/test/java -name '*.java' > build/test-sources.txt
javac --release 17 -encoding UTF-8 -Xlint:all -cp build/classes -d build/test-classes @build/test-sources.txt
java -cp build/classes:build/test-classes edu.ict.shop.ShopTests
