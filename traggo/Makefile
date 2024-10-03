

# TODO update this
NDK_BIN := /home/minno/Android/Sdk/ndk/27.1.12297006/toolchains/llvm/prebuilt/linux-x86_64/bin

# SDK target version
SDK_VER := 24

OUT_DIR := $(abspath ../app/src/main/jniLibs)
TRAGGO_SRC := ./traggo/server

all: setup_traggo build

build: build_arm build_arm64 build_x86 build_x86_64

setup_traggo:
	NODE_OPTIONS="--openssl-legacy-provider" \
				 make -C $(TRAGGO_SRC) \
				 download-tools install generate build-js

build_arm:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/armv7a-linux-androideabi$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="arm" \
	   go build -v \
	   -o "$(OUT_DIR)/armeabi/libtraggo.so" \
	   main.go

build_arm64:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/aarch64-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="arm64" \
	   go build -v \
	   -o "$(OUT_DIR)/arm64-v8a/libtraggo.so" \
	   main.go

build_x86:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/i686-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="386" \
	   go build -v \
	   -o "$(OUT_DIR)/x86/libtraggo.so" \
	   main.go

build_x86_64:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/x86_64-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="amd64" \
	   go build -v \
	   -o "$(OUT_DIR)/x86_64/libtraggo.so" \
	   main.go

