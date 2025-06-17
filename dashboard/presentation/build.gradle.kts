plugins {
    alias(libs.plugins.notemark.android.library.compose)
}

android {
    namespace = "com.icdid.dashboard.presentation"
}

dependencies {
    implementation(projects.dashboard.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
}