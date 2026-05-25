plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "me.leolin.shortcutbadger.example"
    compileSdk = 37

    defaultConfig {
        applicationId = "me.leolin.shortcutbadger.example"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

}

dependencies {
    implementation(project(":ShortcutBadger"))

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
}
