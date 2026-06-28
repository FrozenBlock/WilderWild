plugins {
    id("ww-multiloader-loader")
    id("net.neoforged.moddev")
    id("org.quiltmc.gradle.licenser")
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val frozenlib_version: String by project
val cloth_config_version: String by project
val biolith_version: String by project

val neoforge_version: String by project
val neoforge_loader_version_range: String by project

val neoforgeSnapshotMaven = findProperty("neoforge_snapshot_maven") as String?

base {
    archivesName.set("$archives_base_name-neoforge")
}

version = getModVersion()
group = maven_group

if (!neoforgeSnapshotMaven.isNullOrBlank()) {
    repositories {
        maven(neoforgeSnapshotMaven) { name = "NeoForge Snapshots" }
    }
}

neoForge {
    version = neoforge_version
    val at = rootProject.file("common/src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }
    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
            ideName = "NeoForge ${name.replaceFirstChar { it.uppercase() }} (${project.path})"
        }
        create("client") {
            client()
            gameDirectory.set(project.mkdir(project.file("runs/client")))
        }
        create("server") {
            server()
            project.file("runs/server").parentFile?.mkdirs()
            gameDirectory.set(project.mkdir(project.file("runs/server")))
        }
    }
    mods {
        create(mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    api("net.frozenblock:frozenlib-neoforge:${frozenlib_version}")

    implementation("me.shedaniel.cloth:cloth-config-neoforge:${cloth_config_version}")
    compileOnly("com.terraformersmc:biolith-neoforge:${biolith_version}")
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

    processResources {
        val properties = mapOf("mod_version" to getModVersion())
        inputs.properties(properties)
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(properties)
        }
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
        options.release = 25
        options.isFork = true
        options.isIncremental = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
val loaderVariants = setOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements")
configurations.all {
    if (name in loaderVariants) {
        attributes {
            attribute(loaderAttribute, "neoforge")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "neoforge")
            }
        }
    }
}

fun getModVersion(): String {
    return "$mod_version-mc$minecraft_version"
}
