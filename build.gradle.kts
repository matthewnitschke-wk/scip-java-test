import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id "java"
    id "idea"
    id "application"

    id "org.springframework.boot" version "3.1.2"
    id "org.jetbrains.kotlin.jvm" version "1.8.10"
    id "org.jetbrains.kotlin.plugin.spring" version "1.9.0"

    id "org.openapi.generator" version "6.6.0"
}

test {
    useJUnitPlatform()
    environment 'IAM_UNSAFE', 'true'
    dependsOn "cleanTest"
}

sourceCompatibility = JavaVersion.VERSION_17
targetCompatibility = JavaVersion.VERSION_17
tasks.withType(KotlinCompile).configureEach {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17
}

configurations {
    javaagent
}

task syncJavaAgentDependencies(type: Sync) {
    from configurations.javaagent
    into "build/libs/javaagent"
}

ext {
    spring_boot = '3.0.0'
    junit_jupiter = '5.10.0'
    platform = '1.0.1'
}

dependencies {
    // kotlin stdlib
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10"
    implementation "org.jetbrains.kotlin:kotlin-reflect:1.8.10"

    // Spring Boot Dependencies
    implementation "org.springframework.boot:spring-boot-starter-web:${spring_boot}"
    implementation "org.springframework.boot:spring-boot-devtools:${spring_boot}"
    implementation "org.springframework.boot:spring-boot-starter-security:${spring_boot}"

    // Third Party Maintained Dependencies
    implementation "org.apache.httpcomponents.client5:httpclient5:5.2.1"
    implementation "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider"
    implementation "org.bitbucket.b_c:jose4j:0.9.3"
    implementation "com.launchdarkly:launchdarkly-java-server-sdk:6.0.5"

    // Test Dependencies
    testImplementation "junit:junit:4.13.2"
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junit_jupiter}"
    testImplementation "org.junit.jupiter:junit-jupiter-engine:${junit_jupiter}"
    testImplementation "org.mockito.kotlin:mockito-kotlin:5.0.0"
    testImplementation "org.springframework.boot:spring-boot-starter-test:${spring_boot}"
    testImplementation "com.ninja-squad:springmockk:4.0.0"
    testImplementation "org.springframework.security:spring-security-test:6.1.2"

    // javaagent
    javaagent "io.opentelemetry.javaagent:opentelemetry-javaagent:1.27.0"
}
