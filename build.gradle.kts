import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"

    val kotlinVersion = "1.9.20"

    kotlin("jvm")
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("kapt")
}

allprojects{
    group = "com.adevspoon"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile>{
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")


    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-aop")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.mockk:mockk:1.13.7")

        // Testcontainers - Local & Test 환경에서 사용
        implementation("org.testcontainers:testcontainers:1.19.7")
        implementation("org.testcontainers:localstack:1.19.7")
        // 마이그레이션 완료 후부터 사용하기
//        developmentOnly("org.testcontainers:mysql:1.19.7")
        testImplementation("org.testcontainers:testcontainers:1.19.7")
        testImplementation("org.testcontainers:junit-jupiter:1.19.7")
        testImplementation("org.testcontainers:localstack:1.19.7")
        testImplementation("org.testcontainers:mysql:1.19.7")

        // Annotation Processor
        kapt("org.springframework.boot:spring-boot-configuration-processor")
    }
}

tasks.getByName("bootJar") {
    enabled = false
}