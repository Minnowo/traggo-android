FROM eclipse-temurin:17-jdk-jammy

ENV GO_VERSION 1.23.2

# Can be found scrolling down on this page:
# https://developer.android.com/studio/index.html#command-tools
ARG ANDROID_SDK_FILENAME=commandlinetools-linux-11076708_latest.zip
WORKDIR /opt

# The '--reinstall' combined with 'build-essentials' is necessary for cgo
# compilation of go std libs to work. A terrible, arcane hack to solve arkane
# cgo issues - almost fitting.
RUN apt-get update && \
    apt-get install --reinstall --no-install-recommends -y build-essential gcc unzip git gnupg nodejs

# Install yarn
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | gpg --dearmor | tee /etc/apt/trusted.gpg.d/yarn.gpg && \
    echo "deb [signed-by=/etc/apt/trusted.gpg.d/yarn.gpg] https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
    apt-get update && apt-get install yarn

# Install Go
RUN wget -nv https://dl.google.com/go/go${GO_VERSION}.linux-amd64.tar.gz && \
    tar -zxf go${GO_VERSION}.linux-amd64.tar.gz && \
    rm go${GO_VERSION}.linux-amd64.tar.gz

ENV GOROOT /opt/go
ENV GOPATH /opt/gopath
ENV PATH ${GOROOT}/bin:${GOPATH}/bin:${PATH}

# Install Android SDK manager
RUN mkdir -p /opt/android-sdk && cd /opt/android-sdk && \
    wget -q https://dl.google.com/android/repository/${ANDROID_SDK_FILENAME} && \
    unzip -q ${ANDROID_SDK_FILENAME} && \
    rm ${ANDROID_SDK_FILENAME}
ENV ANDROID_HOME /opt/android-sdk

ARG SDKMANAGER="${ANDROID_HOME}/cmdline-tools/bin/sdkmanager --sdk_root=${ANDROID_HOME}"

# Accept the SDK license, as we can't install packages otherwise
RUN yes | $SDKMANAGER --licenses > /dev/null

# NDK version
ENV NDK_VERSION 27.1.12297006

# Install other android packages, including NDK
RUN $SDKMANAGER tools platform-tools "build-tools;34.0.0" "platforms;android-34" "extras;android;m2repository" "ndk;${NDK_VERSION}"

# Accept licenses of newly installed packages
RUN yes | $SDKMANAGER --licenses

WORKDIR /mnt
