
plugins {
    id("com.adevspoon.kotlin-common-conventions")
}

dependencies {
    implementation(project(":adevspoon-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.slack.api:slack-api-client:1.38.0")
    //querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    runtimeOnly("com.mysql:mysql-connector-j")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

tasks.register("copyConfig", Copy::class) {
    copy {
        from("../adevspoon-config/backend/domain")
        include("*.yml", "*.xml")
        into("src/main/resources")
    }
}