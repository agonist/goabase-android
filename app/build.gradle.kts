plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {

        implementation(Dep.AndroidX.appCompat)
        implementation(Dep.AndroidX.fragmentKtx)
        implementation(Dep.AndroidX.navigationFragmentKtx)
        implementation(Dep.AndroidX.navigationUiKtx)
        implementation(Dep.AndroidX.coreKtx)
        implementation(Dep.AndroidX.lifeCycleLiveData)
        implementation(Dep.AndroidX.alifeCycleExtension)
        implementation(Dep.AndroidX.lifeCycleViewModel)
        implementation(Dep.AndroidX.lifeCycleRuntime)
        implementation(Dep.AndroidX.constraintLayout)

        implementation(Dep.Compose.foundation)
        implementation(Dep.Compose.foundationLayout)
        implementation(Dep.Compose.material)
        implementation(Dep.Compose.uiGraphics)
        implementation(Dep.Compose.ui)
        implementation(Dep.Compose.uiTooling)
        implementation(Dep.Compose.runtimeLiveData)

        implementation(Dep.material)
        implementation(Dep.coroutineCore)
        implementation(Dep.koin)
        implementation(Dep.koinViewmodel)

        implementation(Dep.retrofit)
        implementation(Dep.retrofitMoshi)
        implementation(Dep.moshi)
        implementation(Dep.loggingInterceptor)
        implementation(Dep.threeten)
        implementation(Dep.accompanistCoil)
        implementation(Dep.accompanistInsets)
        implementation(Dep.countryPicker)

        testImplementation(Dep.Test.junit)
        testImplementation(Dep.Test.coroutineTest)
        testImplementation(Dep.Test.androidArchTest)
        testImplementation(Dep.Test.assertj)
        testImplementation(Dep.Test.mockk)

}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.onionsquare.goabase"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 10001
        versionName = "1.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

        getByName("debug") {
            isMinifyEnabled = false
            debuggable(true)
            applicationIdSuffix(".debug")
            versionNameSuffix = "-dev"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-alpha11"
    }

    buildFeatures {
        compose = true
    }
}