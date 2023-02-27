import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "br.com.rodrigogurgel"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


val resilience4jRetrofitVersion = properties["resilience4jRetrofitVersion"]
val resilience4jCircuitBreakerVersion = properties["resilience4jCircuitBreakerVersion"]
val resilience4jRetryVersion = properties["resilience4jRetryVersion"]
val resilience4jRateLimiterVersion = properties["resilience4jRateLimiterVersion"]
val retrofit2Version = properties["retrofit2Version"]
val okhttp3Version = properties["okhttp3Version"]

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofit2Version")
    implementation("io.github.resilience4j:resilience4j-retrofit:$resilience4jRetrofitVersion")
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:$resilience4jCircuitBreakerVersion")
    implementation("io.github.resilience4j:resilience4j-retry:$resilience4jRetryVersion")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:$resilience4jRateLimiterVersion")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
