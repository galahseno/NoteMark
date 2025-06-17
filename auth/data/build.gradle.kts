plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.auth.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)

    implementation(libs.bundles.ktor)
}