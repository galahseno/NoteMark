plugins {
    alias(libs.plugins.notemark.android.library.compose)
}

android {
    namespace = "com.icdid.core.presentation"
}

dependencies {
    implementation(projects.core.domain)
}