#!/bin/bash
set -e

## Build Monomart docker image
echo "Building monomart jar file"
./gradlew clean bootJar

echo "Building monomart docker image"
docker build -t monomart .

## Build AGW docker image
echo "Building AGW jar file"
cd agw
./gradlew clean bootJar

echo "Building AGW docker image"
docker build -t monomart-agw .
cd ..

## Build Commerce docker image
echo "Building Commerce jar file"
cd commerce
./gradlew clean bootJar

echo "Building Commerce docker image"
docker build -t monomart-commerce .
cd ..

## Build Inventory docker image
echo "Building Inventory jar file"
cd inventory
./gradlew clean bootJar

echo "Building Inventory docker image"
docker build -t monomart-inventory .
cd ..
