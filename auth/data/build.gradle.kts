plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.auth.data"
}

dependencies {
    implementation(projects.core.domain)
}