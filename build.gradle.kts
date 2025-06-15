// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.devtools.ksp) apply false
}

// workaround for glide using an old version of javapoet
buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.squareup"
                && requested.name == "javapoet"
            ) {
                useVersion("1.13.0")
            }
        }
    }
}
