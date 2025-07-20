plugins {
    alias(libs.plugins.notemark.android.library)
}

android {
    namespace = "com.icdid.dashboard.data"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.dashboard.domain)

    implementation(libs.bundles.ktor)
}