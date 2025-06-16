plugins {
    alias(libs.plugins.notemark.android.library.compose)
}

android {
    namespace = "com.icdid.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.data)
}