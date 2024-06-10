plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.healthcare.mymolina"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.healthcare.mymolina"
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material3.android)
    implementation("androidx.compose.material3:material3:1.2.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Testing
    // Test rules and transitive dependencies:
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.8.0")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.7")
//    androidTestImplementation ("androidx.compose.ui:ui-test-junit4-android:1.6.7")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("com.google.truth:truth:1.4.2")
    //Hilt

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51")
    //
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    implementation("androidx.work:work-runtime-ktx:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // MockK for mocking
    testImplementation("org.mockito:mockito-core:5.3.0")
    androidTestImplementation("org.mockito:mockito-android:5.3.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")

    implementation("com.google.firebase:firebase-auth:23.0.0")


    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.10.0")
    //GSON
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")
    //okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")


}