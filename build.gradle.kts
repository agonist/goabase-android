buildscript {

    val kotlin_version by extra("1.4.21-2")
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://jitpack.io" )
    }
}
repositories {
    mavenCentral()
    maven("https://jitpack.io" )
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}