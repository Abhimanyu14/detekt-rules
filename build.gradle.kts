plugins {
    kotlin("jvm") version "1.9.24"
    `maven-publish`
}

dependencies {
    compileOnly("io.gitlab.arturbosch.detekt:detekt-api:1.23.6")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.23.6")
    testImplementation("io.kotest:kotest-assertions-core:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
    systemProperty("compile-snippet-tests", project.hasProperty("compile-test-snippets"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            group = "com.github.Abhimanyu14"
            artifactId = "detekt-rules"
            version = "1.0.0"
        }
    }
}
