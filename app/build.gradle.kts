plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.maks_buriak.mychat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.maks_buriak.mychat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

//dependencies {
//    implementation(project(":domain"))
//    implementation(project(":data"))
//
//    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.firebase:firebase-common")
//    implementation("com.google.firebase:firebase-analytics")
//    implementation("com.google.firebase:firebase-database")
//
//    implementation(platform("androidx.compose:compose-bom:2025.05.01"))
//
//    // ViewModel для Compose
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
//
//    val koin_version = "4.0.3"
//    implementation("io.insert-koin:koin-core:$koin_version")
//    implementation("io.insert-koin:koin-android:$koin_version")
//    testImplementation("io.insert-koin:koin-test:$koin_version")
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.foundation)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//}


dependencies {
    // Локальні модулі
    implementation(project(":domain"))
    implementation(project(":data"))

    // Firebase BoM (для автоматичного керування версіями)
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
    implementation("com.google.android.gms:play-services-auth:21.4.0") // Google Sign-In
    implementation("com.google.firebase:firebase-common")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")

    // Compose BoM
    implementation(platform("androidx.compose:compose-bom:2025.05.01"))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.foundation)

    // ViewModel для Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation(libs.androidx.navigation.compose.jvmstubs)

    // Koin
    val koin_version = "4.0.3"
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    testImplementation("io.insert-koin:koin-test:$koin_version")

    // Тестування
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Інструменти для debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}