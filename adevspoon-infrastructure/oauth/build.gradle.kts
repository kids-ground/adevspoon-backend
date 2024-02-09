tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

plugins {
    id("com.adevspoon.kotlin-common-conventions")
}

dependencyManagement {
    imports {
        val springCloudVersion = "2023.0.0"
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    implementation(project(":adevspoon-domain:oauth-core"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
}
