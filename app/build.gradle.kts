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

    with(projects) {
        implementation(auth.data)
        implementation(auth.domain)
        implementation(auth.presentation)

        implementation(core.data)
        implementation(core.domain)
        implementation(core.presentation)

        implementation(dashboard.data)
        implementation(dashboard.domain)
        implementation(dashboard.presentation)
    }
}