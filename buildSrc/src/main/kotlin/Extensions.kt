@file:Suppress("unused")

package routinechecks

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? {
    return add("testImplementation", dependencyNotation)
}

private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency?
    = add("implementation", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: String): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: String): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.addUnitTestDependencies() {
    testImplementation(Deps.JUNIT)
    testImplementation(Deps.KOTLINX_COROUTINES_TEST)

}

fun DependencyHandler.addAndroidXDependencies() {
    implementation(Deps.ANDROIDX_CORE)
    implementation(Deps.ANDROIDX_ACTIVITY)
    implementation(Deps.ANDROIDX_ANNOTATION)
    implementation(Deps.ANDROIDX_APPCOMPAT)
    implementation(Deps.ANDROIDX_APPCOMPAT)
    implementation(Deps.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Deps.ANDROIDX_RECYCLER_VIEW)
    implementation(Deps.ANDROIDX_LIFECYCLE_EXTENSIONS)
    implementation(Deps.ANDROIDX_LIFECYCLE_VIEW_MODEL)
    implementation(Deps.ANDROIDX_LIFECYCLE_RUNTIME)

}

fun DependencyHandler.addCoroutinesDependencies() {
    implementation(Deps.KOTLINX_COROUTINES)
    implementation(Deps.KOTLINX_COROUTINES_ANDROID)
}

fun DependencyHandler.addRoomDependencies() {
    implementation(Deps.ROOM)
    kapt(Deps.ROOM_COMPILER)
}

fun DependencyHandler.addDaggerDependencies() {
    api(Deps.DAGGER_CORE)
    kapt(Deps.DAGGER_COMPILER)
    api(Deps.DAGGER_ANDROID)
    kapt(Deps.DAGGER_PROCESSOR)
}


