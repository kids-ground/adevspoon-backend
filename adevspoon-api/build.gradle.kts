
tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

plugins {
    id("com.adevspoon.kotlin-common-conventions")
}

dependencies {
    implementation(project(":adevspoon-common"))
    implementation(project(":adevspoon-domain"))
    implementation(project(":adevspoon-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.security:spring-security-test")
}

