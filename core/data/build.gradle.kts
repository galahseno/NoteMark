plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.bundles.ktor)
}