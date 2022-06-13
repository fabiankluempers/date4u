plugins {
    kotlin("multiplatform") version "1.6.21"
    application
    kotlin("plugin.serialization") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version  "1.6.21" apply false
    id("org.springframework.boot") version "2.7.0" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

group = "com.materna"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm("spring") {
        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")
        apply(plugin = "org.jetbrains.kotlin.plugin.spring")
        apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js("react", LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
                outputFileName = "main.js"
                outputPath = File(buildDir, "processedResources/spring/main/static")
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val springMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-web")
                implementation("org.springframework.boot:spring-boot-starter-security")
                implementation("io.arrow-kt:arrow-core:1.1.3-alpha.16")
                implementation("org.springframework.boot:spring-boot-starter-data-jpa")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.5")
                implementation("org.postgresql:postgresql")
                implementation("org.jetbrains.kotlin:kotlin-reflect")

            }
        }
        val springTest by getting
        val reactMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.2.1-pre.290-kotlin-1.6.10")
                implementation("io.ktor:ktor-client-js:2.0.2")
                implementation("io.ktor:ktor-client-content-negotiation:2.0.2")
                implementation(npm("react-bootstrap", "2.4.0"))
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.2")
            }
        }
        val reactTest by getting
    }
}

application {
    mainClass.set("com.materna.ApplicationKT")
}

tasks.named<Copy>("springProcessResources") {
    val reactBrowserDistribution = tasks.named("reactBrowserDistribution")
    from(reactBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("springJar"))
    classpath(tasks.named<Jar>("springJar"))
}
