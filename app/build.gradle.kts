@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.yape.food"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yape.food"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //💡a custom hilt test runner based on Android Junit4 runner
        testInstrumentationRunner = "com.yape.food.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE*,NOTICE.txt}"
        }
    }

    testOptions {
        packaging {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {
    //Libraries
    implementation(project(":domain"))

    //Core and foundation
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    //Compose libs
    implementation(libs.androidx.constraintlayout)
    implementation(libs.compose.navigation)
    implementation(libs.coil.compose)

    //Maps
    implementation(libs.maps.compose)
    //implementation(libs.maps.ktx.std)

    //Unit tests
    testImplementation(libs.junit)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)

    //Instrumented
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.navigation.testing)
    //Mockk
    androidTestImplementation(libs.mockk.android)
    //Hilt
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android)
    kspAndroidTest(libs.hilt.android.compiler)

    //Debug
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

secrets {
    //Check this file for more information about the maps apikey
    defaultPropertiesFileName = "local.defaults.properties"
}