
tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

plugins {
    id("com.adevspoon.kotlin-common-conventions")
}

