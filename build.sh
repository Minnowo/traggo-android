#!/bin/sh

# This is for use by the Docker builder

# Build Traggo
make -C ./traggo


# I couldn't figure out how to mount the keystore file so we're doing this
if [ -n "$KEYSTORE_JKS_BASE64" ]; then

    echo "$KEYSTORE_JKS_BASE64" | base64 -d > "$RELEASE_STORE_FILE"
fi


# Build the app
./gradlew --no-daemon build


if [ -e "$RELEASE_STORE_FILE" ]; then

    echo "Release store file exists: $RELEASE_STORE_FILE"
    rm -f "$RELEASE_STORE_FILE"
fi

