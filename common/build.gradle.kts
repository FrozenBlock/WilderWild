plugins {
    id("ww-multiloader-common")
    id("dev.architectury.loom-no-remap")
    id("org.quiltmc.gradle.licenser")
}

val minecraft_version: String by project
val loader_version: String by project

val frozenlib_version: String by project
val cloth_config_version: String by project
val biolith_version: String by project

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val applyLicenses: Task by tasks

sourceSets {
    main {
        resources {
            srcDirs("src/main/generated")
        }
    }
}

loom {
    accessWidenerPath = file("src/main/resources/wilderwild.classtweaker")
    enableTransitiveAccessWideners = true
    interfaceInjection {
        enableDependencyInterfaceInjection = true
    }
}

tasks {
    license {
        if (licenseChecks) {
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")

    // only for @Environment
    api("net.fabricmc:fabric-loader:${loader_version}")

    compileOnly("net.frozenblock:frozenlib-common:${frozenlib_version}")

    compileOnly("net.fabricmc:sponge-mixin:0.17.3+mixin.0.8.7")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.3")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")

    compileOnly("me.shedaniel.cloth:cloth-config:${cloth_config_version}")

    compileOnly("com.terraformersmc:biolith-common:${biolith_version}")
}

val mergeCommonResources by tasks.registering(Sync::class) {
    from(sourceSets.main.get().resources.srcDirs)
    into(layout.buildDirectory.dir("merged-resources"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

configurations {
    create("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    create("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets.main.get().java.sourceDirectories.singleFile)
    add("commonResources", mergeCommonResources.map { it.destinationDir }) {
        builtBy(mergeCommonResources)
    }
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.named(variant) {
        attributes {
            attribute(loaderAttribute, "common")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "common")
            }
        }
    }
}
