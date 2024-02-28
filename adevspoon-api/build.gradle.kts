
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
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.security:spring-security-test")
}

tasks.register("copyConfig", Copy::class) {
    copy {
        from("../adevspoon-config/backend/api")
        include("*.yml", "*.xml")
        into("src/main/resources")
    }
}

tasks.assemble {
    dependsOn(":adevspoon-common", ":adevspoon-domain", ":adevspoon-infrastructure")
}