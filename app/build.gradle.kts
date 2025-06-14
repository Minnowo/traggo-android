plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jmailen.kotlinter") version "4.4.1"
}

android {
    namespace = "com.github.traggo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.traggo"
        minSdk = 24
        targetSdk = 34
        versionCode = 6
        versionName = "0.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("x86", "x86_64", "armeabi", "arm64-v8a")
            isUniversalApk = true
        }
    }

    signingConfigs {
        create("release") {

            val storeFilPath = System.getenv("RELEASE_STORE_FILE")

            println("Key Store file: $storeFilPath")

            storeFile = storeFilPath?.let(::file)

            println("Key Store exists: ${storeFile?.exists()}")

            storePassword = System.getenv("RELEASE_STORE_PASSWORD")
            keyAlias = System.getenv("RELEASE_KEY_ALIAS")
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD")

            // enableV1Signing = true
            // enableV2Signing = true
            // enableV3Signing = true
            // enableV4Signing = true
        }
    }


    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            isRenderscriptDebuggable = true
        }
        getByName("release") {

            isMinifyEnabled = true

            signingConfig = signingConfigs.runCatching { getByName("release") }
                .getOrNull()
                .takeIf { it?.storeFile != null }

            println("Found signing config: ${signingConfig != null}");

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    kotlinter {
        failBuildWhenCannotAutoFormat = true
        ignoreFailures = false
        reporters = arrayOf("checkstyle", "plain")
    }

    packaging {

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        // needed otherwise we can't find traggo.so
        jniLibs {
            useLegacyPackaging = true
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.preference)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
