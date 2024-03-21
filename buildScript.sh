#!/bin/bash
set -e

## Build Monomart docker image
echo "Building monomart jar file"
./gradlew clean bootJar

echo "Building monomart docker image"
docker build -t monomart .
