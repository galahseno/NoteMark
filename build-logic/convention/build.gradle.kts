plugins {
    `kotlin-dsl`
}

group = "com.icdid.build-logic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
   plugins {
        register("androidApp") {
            id = "notemark.android.application"
            implementationClass = "AndroidAppConventionPlugin"
        }
        register("androidLib") {
            id = "notemark.android.library"
            implementationClass = "AndroidLibConventionPlugin"
        }
        register("androidLibCompose") {
            id = "notemark.android.library.compose"
            implementationClass = "AndroidLibComposeConventionPlugin"
        }
        register("jvmLibrary") {
            id = "notemark.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
   }
}