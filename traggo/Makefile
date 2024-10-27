

# The path and version of your Android NDK
NDK_VER ?= 27.1.12297006
NDK_BIN ?= /opt/android-sdk/ndk/$(NDK_VER)/toolchains/llvm/prebuilt/linux-x86_64/bin

# SDK min target version
SDK_VER := 24

OUT_DIR := $(abspath ../app/src/main/jniLibs)
TRAGGO_SRC := ./traggo/server

DATE    :=$(shell date -u +"%Y-%m-%dT%H:%M:%SZ")
COMMIT  :=$(shell git -C $(TRAGGO_SRC) rev-parse --verify HEAD)
VERSION :=$(shell git -C $(TRAGGO_SRC) describe --tags --abbrev=0 | cut -c 2-)

all:
	echo "We got commit '$(COMMIT)' and version '$(VERSION)'"
