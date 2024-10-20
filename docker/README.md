
# Builder image

## Create and use the builder

```sh
git clone --recursive https://github.com/minnowo/traggo-android

# make sure you're in the docker folder
cd traggo-android/docker

# build the image
docker build -t traggo-android-builder:latest -f ./Dockerfile .

cd ..

# build everything
docker run --rm -it -v .:/mnt traggo-android-builder sh build.sh
```

