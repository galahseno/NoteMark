plugins {
    alias(libs.plugins.notemark.android.library.compose)
}

android {
    namespace = "com.icdid.auth.presentation"
}

dependencies {
    with(projects) {
        implementation(auth.domain)
        implementation(core.domain)
        implementation(core.presentation)
        implementation(core.data)
    }
}