package routinechecks

object Deps {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:1.3.72"

    private const val COROUTINES_VERSION = "1.3.7"
    const val KOTLINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
    const val KOTLINX_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"

    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-ktx:1.1.0"
    const val ANDROIDX_ANNOTATION = "androidx.annotation:annotation:1.1.0"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.2.0"
    const val ANDROIDX_RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.1.0"

    private const val LIFECYCLE_VERSION = "2.2.0"
    const val ANDROIDX_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
    const val ANDROIDX_LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val ANDROIDX_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"

    const val MATERIAL = "com.google.android.material:material:1.1.0"

    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"

    //work manager
    const val ANDROID_WORKER = "android.arch.work:work-runtime:1.0.1"

    private const val DAGGER_VERSION = "2.18"
    const val DAGGER_CORE =  "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_ANDROID =  "com.google.dagger:dagger-android-support:$DAGGER_VERSION"
    const val DAGGER_COMPILER =  "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    const val DAGGER_PROCESSOR =  "com.google.dagger:dagger-android-processor:$DAGGER_VERSION"

    // ROOM
    private const val ROOM_VERSION = "2.2.5"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    const val ROOM = "androidx.room:room-ktx:$ROOM_VERSION"

    // TEST
    const val JUNIT = "junit:junit:4.13"
    const val KOTLINX_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"

}