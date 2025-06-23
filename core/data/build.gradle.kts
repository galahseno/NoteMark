plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)

    implementation(libs.timber)
}