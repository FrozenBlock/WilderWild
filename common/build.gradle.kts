plugins {
    id("ww-multiloader-common")
    id("net.neoforged.moddev")
    id("org.quiltmc.gradle.licenser")
}

val neo_form_version: String by project
val neoforgeSnapshotMaven = findProperty("neoforge_snapshot_maven") as String?

val frozenlib_version: String by project
val cloth_config_version: String by project
val biolith_version: String by project

if (!neoforgeSnapshotMaven.isNullOrBlank()) {
    repositories {
        maven(neoforgeSnapshotMaven) { name = "NeoForge Snapshots" }
    }
}

neoForge {
    neoFormVersion = neo_form_version
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val applyLicenses: Task by tasks

tasks {
    license {
        if (licenseChecks) {
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }
}

dependencies {
    compileOnly("net.frozenblock:frozenlib-common:${frozenlib_version}")

    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.3")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")

    compileOnly("me.shedaniel.cloth:cloth-config:${cloth_config_version}")

    compileOnly("com.terraformersmc:biolith-common:${biolith_version}")
}

sourceSets {
    main {
        resources {
            srcDir("src/main/generated")
        }
    }
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
