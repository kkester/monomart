#!/bin/bash
set -e

echo "Building monomart docker image"
./gradlew bootBuildImage --imageName=monomart

echo "Building AGW docker image"
cd agw
./gradlew bootBuildImage --imageName=monomart-agw
cd ..

echo "Building Commerce docker image"
cd commerce
./gradlew bootBuildImage --imageName=monomart-commerce
cd ..

echo "Building Inventory docker image"
cd inventory
./gradlew bootBuildImage --imageName=monomart-inventory
cd ..
