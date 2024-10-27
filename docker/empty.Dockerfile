FROM eclipse-temurin:17-jdk-jammy

ENV GO_VERSION 1.23.2

WORKDIR /opt

# The '--reinstall' combined with 'build-essentials' is necessary for cgo
# compilation of go std libs to work. A terrible, arcane hack to solve arkane
# cgo issues - almost fitting.
RUN apt-get update && \
    apt-get install --reinstall --no-install-recommends -y build-essential git

WORKDIR /mnt
