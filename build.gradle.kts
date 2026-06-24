import groovy.xml.XmlSlurper
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties
import java.io.FileNotFoundException
import java.net.URL

plugins {
    id("net.fabricmc.fabric-loom") version("1.17-SNAPSHOT")
    id("org.quiltmc.gradle.licenser") version("+")
    id("org.ajoberstar.grgit") version("+")
    id("me.modmuss50.mod-publish-plugin") version("+")
    `maven-publish`
    eclipse
    idea
    `java-library`
    java
    checkstyle
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val minecraft_version: String by project
val loader_version: String by project
val min_loader_version: String by project

val mod_id: String by project
val mod_version: String by project
val protocol_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val fabric_api_version: String by project
val frozenlib_version: String by project

val modmenu_version: String by project
val cloth_config_version: String by project
val copperpipes_version: String by project
val terrablender_version: String by project

val biolith_version: String by project
val run_biolith: String by project
val shouldRunBiolith = run_biolith == "true"

val sodium_version: String by project
val run_sodium: String by project
val shouldRunSodium = run_sodium == "true"

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

    accessWidenerPath.set(file("src/main/resources/$mod_id.classtweaker"))
    interfaceInjection {
        // When enabled, injected interfaces from dependencies will be applied.
        enableDependencyInterfaceInjection.set(true)
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
            name("Run Client")
            vmArg("-DMC_DEBUG_FROZENLIB_WIND_DISTURBANCES=true")
            vmArg("-DMC_DEBUG_ENABLED=true")
            vmArg("-DMC_DEBUG_FROZENLIB_WIND=true")

            ideConfigGenerated(true)
        }

        named("server") {
            ideConfigGenerated(true)
        }
    }
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val includeImplementation by configurations.creating

configurations {
    include {
        extendsFrom(includeImplementation)
    }
    implementation {
        extendsFrom(includeImplementation)
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
    maven("https://maven.blamejared.com")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.jamieswhiteshirt.com/libs-release") {
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }
    maven("https://maven.frozenblock.net/release") {
        name = "FrozenBlock"
    }
    maven("https://maven.frozenblock.net/snapshot") {
        name = "FrozenBlock Snapshot"
    }

    flatDir {
        dirs("libs")
    }
    mavenCentral()
}

dependencies {
    // To change the versions, see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraft_version")
    implementation("net.fabricmc:fabric-loader:$loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    // FrozenLib
    api("maven.modrinth:frozenlib:${frozenlib_version}")

    // Simple Copper Pipes
    compileOnlyApi("maven.modrinth:simple-copper-pipes:${copperpipes_version}")

    // Mod Menu
    compileOnly("com.terraformersmc:modmenu:$modmenu_version")

    // Cloth Config
    compileOnly("me.shedaniel.cloth:cloth-config-fabric:$cloth_config_version") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "com.terraformersmc")
    }

    // TerraBlender
    compileOnly("maven.modrinth:terrablender:${terrablender_version}")

    // Biolith
    if (shouldRunBiolith)
        implementation("maven.modrinth:biolith:${biolith_version}")
    else
        compileOnly("maven.modrinth:biolith:${biolith_version}")

    // Sodium
    if (shouldRunSodium)
        implementation("maven.modrinth:sodium:${sodium_version}")
    else
        compileOnly("maven.modrinth:sodium:${sodium_version}")

    "datagenImplementation"(sourceSets.main.get().output)
}

tasks {
    processResources {
        val properties = mapOf(
            "mod_id" to mod_id,
            "version" to version,
            "protocol_version" to protocol_version,
            "minecraft_version" to "~26.3-",//minecraft_version,

            "fabric_loader_version" to ">=$min_loader_version",
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
                "**/*.classtweaker",
                "**/*.nbt",
                "**/*.png",
                "**/*.ogg",
                "**/*.mixins.json",
                "**/*.zip"
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
        // Minecraft 26.1 (26.1-snapshot-1) upwards uses Java 25.
        options.release.set(25)
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

val jar: Jar by tasks
val sourcesJar: Jar by tasks
val javadocJar: Jar by tasks

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25

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
    var version = "$mod_version-mc$minecraft_version"

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
val curseforge_id: String by extra
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

publishMods {
    version.set(modrinth_version)
    file.set(jar.archiveFile)
    changelog.set(changelog_text)
    type.set(STABLE)
    modLoaders.add("fabric")
    //additionalFiles.from(sourcesJar.archiveFile, javadocJar.archiveFile)

    curseforge {
        version.set(modrinth_version)
        projectId.set(curseforge_id)
        projectSlug.set("wilder-wild")
        accessToken.set(providers.environmentVariable("CURSEFORGE_TOKEN"))
        minecraftVersions.add(minecraft_version)
        client = true
        server = true
        requires("fabric-api")
        requires("frozenlib")
        optional("modmenu")
        optional("cloth-config")
        optional("simple-copper-pipes")
        optional("trailier-tales")
        optional("glowtone")
        optional("the-copperier-age")
    }
    modrinth {
        version.set(modrinth_version)
        projectId.set(modrinth_id)
        accessToken.set(providers.environmentVariable("MODRINTH_TOKEN"))
        minecraftVersions.add(minecraft_version)
        requires("fabric-api")
        requires("frozenlib")
        optional("modmenu")
        optional("cloth-config")
        optional("simple-copper-pipes")
        optional("trailier-tales")
        optional("glowtone")
        optional("the-copperier-age")
    }
    github {
        version.set(modrinth_version)
        repository.set("FrozenBlock/WilderWild")
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))
        commitish.set(getBranch())
        additionalFiles.from(sourcesJar.archiveFile.get().asFile, javadocJar.archiveFile.get().asFile)
    }
}

tasks.named("publishCurseforge") {
    dependsOn(tasks.jar)
}
tasks.named("publishModrinth") {
    dependsOn(tasks.jar)
}
tasks.named("publishGithub") {
    dependsOn(tasks.jar)
    dependsOn(sourcesJar)
    dependsOn(javadocJar)
}

val publishMod by tasks.register("publishMod") {
    dependsOn(tasks.publish)
    dependsOn(tasks.publishMods)
}
