#!/bin/sh

git config --global --add safe.directory /mnt

# hopfully this has a message for us
git --version

git branch

git submodule

git -C ./traggo/traggo/server rev-parse --verify HEAD

git -C ./traggo/traggo/server describe --tags --abbrev=0 | cut -c 2-

git -C ./traggo/traggo/server describe --tags --abbrev=0

