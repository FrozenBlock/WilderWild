import groovy.xml.XmlSlurper
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties
import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub
import java.io.FileNotFoundException
import java.net.URL

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.kohsuke:github-api:+")

        // remove these 2 to get normal fabric loom versions
        classpath(files("libs/fabric-loom-1.5.local.jar"))
        classpath("net.fabricmc:mapping-io:+")
    }
}

plugins {
    id("fabric-loom") version("+")
    id("org.quiltmc.gradle.licenser") version("+")
    id("org.ajoberstar.grgit") version("+")
    id("com.modrinth.minotaur") version("+")
    `maven-publish`
    eclipse
    idea
    `java-library`
    java
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val minecraft_version: String by project
val quilt_mappings: String by project
val parchment_mappings: String by project
val loader_version: String by project

val mod_id: String by project
val mod_version: String by project
val protocol_version: String by project
val mod_loader: String by project
val maven_group: String by project
val archives_base_name: String by project

val fabric_api_version: String by project
val mixin_extras_version: String by project
val fabric_asm_version: String by project
val frozenlib_version: String by project

val betterend_version: String by project
val betternether_version: String by project
val modmenu_version: String by project
val cloth_config_version: String by project
val copperpipes_version: String by project
val terrablender_version: String by project
val terralith_version: String by project
val fallingleaves_version: String by project

val sodium_version: String by project
val run_sodium: String by project
val shouldRunSodium = run_sodium == "true"

val embeddium_version: String by project
val run_embeddium: String by project
val shouldRunEmbeddium = run_embeddium == "true"

base {
    archivesName = archives_base_name
}

version = getModVersion()
group = maven_group

val local_frozenlib = findProject(":FrozenLib") != null
val release = findProperty("releaseType") == "stable"

val datagen by sourceSets.registering {
    compileClasspath += sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().runtimeClasspath
}

loom {
    runtimeOnlyLog4j.set(true)

    mixin {
        defaultRefmapName.set("mixins.$mod_id.refmap.json")
    }

    accessWidenerPath.set(file("src/main/resources/$mod_id.accesswidener"))
    interfaceInjection {
        // When enabled, injected interfaces from dependencies will be applied.
        enableDependencyInterfaceInjection.set(false)
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/generated")
        }
    }
}

loom {
    runs {
        register("datagen") {
            client()
            name("Data Generation")
            source(datagen.get())
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            //vmArg("-Dfabric-api.datagen.strict-validation")
            vmArg("-Dfabric-api.datagen.modid=$mod_id")

            ideConfigGenerated(true)
            runDir = "build/datagen"
        }

        named("client") {
            ideConfigGenerated(true)
        }
        named("server") {
            ideConfigGenerated(true)
        }
    }
}

val includeModImplementation by configurations.creating
val includeImplementation by configurations.creating

configurations {
    include {
        extendsFrom(includeImplementation)
        extendsFrom(includeModImplementation)
    }
    implementation {
        extendsFrom(includeImplementation)
    }
    modImplementation {
        extendsFrom(includeModImplementation)
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    maven("https://jitpack.io")
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven") {
                name = "Modrinth"
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://maven.terraformersmc.com") {
        content {
            includeGroup("com.terraformersmc")
        }
    }

    maven("https://maven.shedaniel.me/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.parchmentmc.org")
    maven("https://maven.quiltmc.org/repository/release") {
        name = "Quilt"
    }
    maven("https://maven.jamieswhiteshirt.com/libs-release") {
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }

    flatDir {
        dirs("libs")
    }
    mavenCentral()
}

dependencies {
    // To change the versions, see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings(loom.layered {
        // please annoy treetrain if this doesnt work
        mappings("org.quiltmc:quilt-mappings:$quilt_mappings:intermediary-v2")
        parchment("org.parchmentmc.data:parchment-$parchment_mappings@zip")
        officialMojangMappings {
            nameSyntheticMembers = false
        }
    })
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    // FrozenLib
    if (local_frozenlib)
        api(project(":FrozenLib", configuration = "namedElements"))?.let { include(it) }
    else
        modApi("maven.modrinth:frozenlib:$frozenlib_version")?.let { include(it) }

    // Simple Copper Pipes
    modCompileOnlyApi("maven.modrinth:simple-copper-pipes:${copperpipes_version}")

    // Mod Menu
    modImplementation("com.terraformersmc:modmenu:$modmenu_version")

    // Cloth Config
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:$cloth_config_version") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "com.terraformersmc")
    }

    // TerraBlender
    modCompileOnlyApi("com.github.glitchfiend:TerraBlender-fabric:${terrablender_version}")

    // Particle Rain
    modCompileOnly("maven.modrinth:particle-rain:v2.0.5")

    // MixinExtras
    // keep until Fabric applies the annotation processor by default
    modApi("io.github.llamalad7:mixinextras-fabric:$mixin_extras_version")?.let { annotationProcessor(it) }


    // Embeddium
    if (shouldRunEmbeddium)
        modImplementation("maven.modrinth:embeddium:${embeddium_version}")
    else
        modCompileOnly("maven.modrinth:embeddium:${embeddium_version}")

    // FallingLeaves
    modCompileOnly("maven.modrinth:fallingleaves:${fallingleaves_version}")

    // BetterEnd
    modCompileOnly("maven.modrinth:betterend:${betterend_version}")

    // BetterNether
    modCompileOnly("maven.modrinth:betternether:${betternether_version}")

    "datagenImplementation"(sourceSets.main.get().output)
}

tasks {
    processResources {
        val properties = mapOf(
            "mod_id" to mod_id,
            "version" to version,
            "protocol_version" to protocol_version,
            "minecraft_version" to "~1.20.5-",

            "fabric_api_version" to ">=$fabric_api_version",
            "frozenlib_version" to ">=${frozenlib_version.split('-').firstOrNull()}-"
        )

        properties.forEach { (a, b) -> inputs.property(a, b) }

        filesNotMatching(
            listOf(
                "**/*.java",
                "**/sounds.json",
                "**/lang/*.json",
                "**/.cache/*",
                "**/*.accesswidener",
                "**/*.nbt",
                "**/*.png",
                "**/*.ogg",
                "**/*.mixins.json"
            )
        ) {
            expand(properties)
        }
    }

    license {
        if (licenseChecks) {
            rule(file("codeformat/HEADER"))

            include("**/*.java")
        }
    }


    register("javadocJar", Jar::class) {
        dependsOn(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc.get().destinationDir)
    }

    register("sourcesJar", Jar::class) {
        dependsOn(classes)
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
        // Minecraft 1.20.5 (24w14a) upwards uses Java 21.
        options.release.set(21)
        options.isFork = true
        options.isIncremental = true
    }

    withType(Test::class) {
        maxParallelForks = Runtime.getRuntime().availableProcessors().div(2)
    }
}

val applyLicenses: Task by tasks
val test: Task by tasks
val runClient: Task by tasks
val runDatagen: Task by tasks

val remapJar: Task by tasks
val sourcesJar: Task by tasks
val javadocJar: Task by tasks

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

fun getModVersion(): String {
    var version = "$mod_version-$mod_loader+$minecraft_version"

    if (release != null && !release) {
        //version += "-unstable"
    }

    return version
}

val env = System.getenv()

publishing {
    val mavenUrl = env["MAVEN_URL"]
    val mavenUsername = env["MAVEN_USERNAME"]
    val mavenPassword = env["MAVEN_PASSWORD"]

    val release = mavenUrl?.contains("release")
    val snapshot = mavenUrl?.contains("snapshot")

    val publishingValid = rootProject == project && !mavenUrl.isNullOrEmpty() && !mavenUsername.isNullOrEmpty() && !mavenPassword.isNullOrEmpty()

    val publishVersion = makeModrinthVersion(mod_version)
    val snapshotPublishVersion = publishVersion + if (snapshot == true) "-SNAPSHOT" else ""

    val publishGroup = rootProject.group.toString().trim(' ')

    val hash = if (grgit.branch != null && grgit.branch.current() != null) grgit.branch.current().fullName else ""

    publications {
        var publish = true
        try {
            if (publishingValid) {
                try {
                    val xml = ResourceGroovyMethods.getText(URL("$mavenUrl/${publishGroup.replace('.', '/')}/$snapshotPublishVersion/$publishVersion.pom"))
                    val metadata = XmlSlurper().parseText(xml)

                    if (metadata.getProperty("hash").equals(hash)) {
                        publish = false
                    }
                } catch (ignored: FileNotFoundException) {
                    // No existing version was published, so we can publish
                }
            } else {
                publish = false
            }
        } catch (e: Exception) {
            publish = false
            println("Unable to publish to maven. The maven server may be offline.")
        }

        if (publish) {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                artifact(javadocJar)

                pom {
                    groupId = publishGroup
                    artifactId = rootProject.base.archivesName.get().lowercase()
                    version = snapshotPublishVersion
                    withXml {
                        asNode().appendNode("properties").appendNode("hash", hash)
                    }
                }
            }
        }
    }
    repositories {

        if (publishingValid) {
            maven {
                url = uri(mavenUrl!!)

                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        } else {
            mavenLocal()
        }
    }
}

extra {
    val properties = Properties()
    properties.load(FileInputStream(file("gradle/publishing.properties")))
    properties.forEach { (a, b) ->
        project.extra[a as String] = b as String
    }
}

val modrinth_id: String by extra
val release_type: String by extra
val changelog_file: String by extra

val modrinth_version = makeModrinthVersion(mod_version)
val display_name = makeName(mod_version)
val changelog_text = getChangelog(file(changelog_file))

fun makeName(version: String): String {
    return "${version} (${minecraft_version})"
}

fun makeModrinthVersion(version: String): String {
    return "$version-mc${minecraft_version}"
}

fun getChangelog(changelogFile: File): String {
    val text = Files.readString(changelogFile.toPath())
    val split = text.split("-----------------")
    if (split.size != 2)
        throw IllegalStateException("Malformed changelog")
    return split[1].trim()
}

fun getBranch(): String {
    val env = System.getenv()
    var branch = env["GITHUB_REF"]
    if (branch != null && branch != "") {
        return branch.substring(branch.lastIndexOf("/") + 1)
    }

    if (grgit == null) {
        return "unknown"
    }

    branch = grgit.branch.current().name
    return branch.substring(branch.lastIndexOf("/") + 1)
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set(modrinth_id)
    versionNumber.set(modrinth_version)
    versionName.set(display_name)
    versionType.set(release_type)
    changelog.set(changelog_text)
    uploadFile.set(remapJar)
    gameVersions.set(listOf(minecraft_version))
    loaders.set(listOf("fabric"))
    additionalFiles.set(
        listOf(
            //tasks.remapSourcesJar.get(),
            //javadocJar
        )
    )
    dependencies {
        required.project("fabric-api")
        optional.project("cloth-config")
        optional.project("modmenu")
        optional.project("simple-copper-pipes")
        embedded.project("frozenlib")
    }
}


val github by tasks.register("github") {
    dependsOn(remapJar)
    val env = System.getenv()
    val token = env["GITHUB_TOKEN"]
    val repoVar = env["GITHUB_REPOSITORY"]
    onlyIf {
        token != null && token != ""
    }

    doLast {
        val github = GitHub.connectUsingOAuth(token)
        val repository = github.getRepository(repoVar)

        val releaseBuilder = GHReleaseBuilder(repository, makeModrinthVersion(mod_version))
        releaseBuilder.name(makeName(mod_version))
        releaseBuilder.body(changelog_text)
        releaseBuilder.commitish(getBranch())
        releaseBuilder.prerelease(release_type != "release")

        val ghRelease = releaseBuilder.create()
        ghRelease.uploadAsset(tasks.remapJar.get().archiveFile.get().asFile, "application/java-archive")
        ghRelease.uploadAsset(tasks.remapSourcesJar.get().archiveFile.get().asFile, "application/java-archive")
        ghRelease.uploadAsset(javadocJar.outputs.files.singleFile, "application/java-archive")
    }
}

val publishMod by tasks.register("publishMod") {
    dependsOn(tasks.publish)
    dependsOn(github)
    dependsOn(tasks.modrinth)
}
