#!/bin/sh

# This is for use by the Docker builder
# docker run --rm -it -v .:/mnt traggo-android-builder sh build.sh

# Build Traggo
make -C ./traggo

# Build the app
./gradlew build

