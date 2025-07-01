import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("io.freefair.lombok") version "8.10"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dev.jsinco.luma.chaticons"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.jsinco.dev/releases")
    maven("https://repo.opencollab.dev/main/")
    maven("https://maven.devs.beer/")
}

dependencies {
    compileOnly("dev.jsinco.chatheads:ChatHeads:1.7")
    compileOnly("dev.jsinco.luma.lumacore:LumaCore:776c4e4")
    compileOnly("org.geysermc.floodgate:api:2.2.2-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
    compileOnly("dev.lone:api-itemsadder:4.0.10")
    compileOnly("net.luckperms:api:5.4")
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.5")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.5")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    shadowJar {
        relocate("eu.okaeri", "dev.jsinco.luma.chaticons.okaeri")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    jar {
        enabled = false
    }
    processResources {
        outputs.upToDateWhen { false }
        filter<ReplaceTokens>(mapOf(
            "tokens" to mapOf("version" to project.version.toString()),
            "beginToken" to "\${",
            "endToken" to "}"
        ))
    }
    build {
        dependsOn(shadowJar)
    }
}