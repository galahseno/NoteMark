plugins {
    alias(libs.plugins.notemark.android.library.compose)
}

android {
    namespace = "com.icdid.dashboard.presentation"
}

dependencies {
    with(projects) {
        implementation(dashboard.domain)
        implementation(core.domain)
        implementation(core.presentation)
    }
}