plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.dashboard.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.dashboard.domain)
}