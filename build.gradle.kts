import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js") version "1.3.72"
}

repositories {
    jcenter()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("pl.karol202.uranium:web-canvas:0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
}

kotlin.target.browser { }

tasks.withType(Kotlin2JsCompile::class) {
    kotlinOptions.freeCompilerArgs += listOf("-Xopt-in=kotlin.time.ExperimentalTime")
}
