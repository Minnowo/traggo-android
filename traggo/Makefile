

# The path and version of your Android NDK
NDK_VER ?= 27.1.12297006
NDK_BIN ?= /opt/android-sdk/ndk/$(NDK_VER)/toolchains/llvm/prebuilt/linux-x86_64/bin

# SDK min target version
SDK_VER := 24

OUT_DIR := $(abspath ../app/src/main/jniLibs)
TRAGGO_SRC := ./traggo/server

DATE=$(shell date -u +"%Y-%m-%dT%H:%M:%SZ")
COMMIT=$(shell git -C $(TRAGGO_SRC) rev-parse --verify HEAD)
VERSION=$(shell git -C $(TRAGGO_SRC) describe --tags --abbrev=0 | cut -c 2-)

all: setup_traggo build

build: build_arm build_arm64 build_x86 build_x86_64

setup_traggo:
	@if make -C $(TRAGGO_SRC) download-tools install generate build-js; then \
		echo "Build succeeded without NODE_OPTIONS"; \
	else \
		echo "Build failed, trying with NODE_OPTIONS"; \
		NODE_OPTIONS="--openssl-legacy-provider" \
				 make -C $(TRAGGO_SRC) download-tools install generate build-js; \
	fi

build_arm:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/armv7a-linux-androideabi$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="arm" \
	   go build -v \
	   -ldflags "-X main.BuildDate=$(DATE) -X main.BuildMode=prod -X main.BuildCommit=$(COMMIT) -X main.BuildVersion=$(VERSION)" \
	   -o "$(OUT_DIR)/armeabi/libtraggo.so" \
	   main.go

build_arm64:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/aarch64-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="arm64" \
	   go build -v \
	   -ldflags "-X main.BuildDate=$(DATE) -X main.BuildMode=prod -X main.BuildCommit=$(COMMIT) -X main.BuildVersion=$(VERSION)" \
	   -o "$(OUT_DIR)/arm64-v8a/libtraggo.so" \
	   main.go

build_x86:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/i686-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="386" \
	   go build -v \
	   -ldflags "-X main.BuildDate=$(DATE) -X main.BuildMode=prod -X main.BuildCommit=$(COMMIT) -X main.BuildVersion=$(VERSION)" \
	   -o "$(OUT_DIR)/x86/libtraggo.so" \
	   main.go

build_x86_64:

	cd $(TRAGGO_SRC) && \
	   CC="$(NDK_BIN)/x86_64-linux-android$(SDK_VER)-clang" \
	   CGO_ENABLED=1 \
	   GOOS="android" \
	   GOARCH="amd64" \
	   go build -v \
	   -ldflags "-X main.BuildDate=$(DATE) -X main.BuildMode=prod -X main.BuildCommit=$(COMMIT) -X main.BuildVersion=$(VERSION)" \
	   -o "$(OUT_DIR)/x86_64/libtraggo.so" \
	   main.go

