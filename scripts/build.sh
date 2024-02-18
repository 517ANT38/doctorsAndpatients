#!/bin/sh
gd=docker
gr=$(groups "$USER" | grep -o -w "\b$gd\b")

if [ "$gd" = "$gr" ]; then
    cd $(dirname $0)/.. || exit 1
    cd doctorsPatients
    DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/data_service .
    cd ..
    cd apiDoctors
    DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/api_doctors .
    cd ..
else
    echo "User $USER is not a member of the Docker group."
    exit 1
fi