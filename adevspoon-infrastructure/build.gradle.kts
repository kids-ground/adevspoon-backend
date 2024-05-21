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
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }
}


dependencies {
    implementation(project(":adevspoon-common"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.676")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("com.google.firebase:firebase-admin:9.2.0")
    implementation("com.slack.api:slack-api-client:1.38.0")
}
