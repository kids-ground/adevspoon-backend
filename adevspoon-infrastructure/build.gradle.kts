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
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

}

