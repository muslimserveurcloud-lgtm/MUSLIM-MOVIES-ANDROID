plugins {
    id("com.android.application")
}

android {
    namespace = "com.muslimmovies.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.muslimmovies.app"
        minSdk = 23
        targetSdk = 35
        versionCode = 2
        versionName = "2.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    implementation("androidx.media3:media3-ui:1.5.1")
}
