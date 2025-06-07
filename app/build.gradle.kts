plugins {
    alias(libs.plugins.notemark.android.application)
}

android {
    namespace = "com.icdid.notemark"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    // Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Timber
    implementation(libs.timber)

    implementation(projects.auth.data)
    implementation(projects.auth.domain)
    implementation(projects.auth.presentation)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
}