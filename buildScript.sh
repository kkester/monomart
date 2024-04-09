#!/bin/bash
set -e

echo "Building monomart docker image"
echo "************** Building Monomart jar file **************"
./gradlew clean bootJar

echo "************** Building Monomart docker image **************"
docker build -t monomart .

echo "Building AGW docker image"
echo "************** Building AGW jar file **************"
cd agw
./gradlew clean bootJar

echo "************** Building AGW docker image **************"
docker build -t monomart-agw .
cd ..

echo "Building Commerce docker image"
echo "************** Building Commerce jar file **************"
cd commerce
./gradlew clean bootJar

echo "************** Building Commerce docker image **************"
docker build -t monomart-commerce .
cd ..

echo "Building Inventory docker image"
echo "************** Building Inventory jar file **************"
cd inventory
./gradlew clean bootJar

echo "************** Building Inventory docker image **************"
docker build -t monomart-inventory .
cd ..
