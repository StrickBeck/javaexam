#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
rm -rf build/classes
mkdir -p build/classes dist
find src/main/java -name '*.java' > build/main-sources.txt
javac --release 17 -encoding UTF-8 -Xlint:all -d build/classes @build/main-sources.txt
jar --create --file dist/ict-shop.jar --main-class edu.ict.shop.Main -C build/classes .
echo "Build successful: dist/ict-shop.jar"
