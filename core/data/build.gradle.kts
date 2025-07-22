plugins {
    alias(libs.plugins.notemark.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.icdid.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.work)

    implementation(libs.bundles.ktor)
    implementation(libs.bundles.room)

    ksp(libs.room.compiler)

    implementation(libs.timber)
}