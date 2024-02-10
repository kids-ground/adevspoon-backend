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
    imports{
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}


dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
}

