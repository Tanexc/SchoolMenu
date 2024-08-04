buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            setUrl("https://www.jitpack.io")
            setUrl("https://maven.google.com/")
        }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.serialization) apply false
}