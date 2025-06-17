plugins {
    alias(libs.plugins.notemark.jvm.library)
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}
