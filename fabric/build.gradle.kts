import groovy.xml.XmlSlurper
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Files
import java.util.Properties

plugins {
    id("com.possible-triangle.fabric")
    id("org.ajoberstar.grgit")
    id("org.quiltmc.gradle.licenser")
    id("me.modmuss50.mod-publish-plugin")
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val minecraft_version: String by project
val fabric_loader_version: String by project
val min_fabric_loader_version: String by project

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

val release = findProperty("releaseType") == "stable"

fabric {
    dependOn(project(":ww-common"))
    accessWidener(project(":ww-common"))
    dataGen {
        owner = project(":ww-common")
        splitSourceSet("datagen")
    }
}

repositories {
    flatDir {
        dirs("libs")
    }
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
val loaderVariants = setOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements", "includeInternal", "modCompileClasspath")
configurations.all {
    if (name in loaderVariants) {
        attributes {
            attribute(loaderAttribute, "fabric")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "fabric")
            }
        }
    }
}

dependencies {
    implementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    // FrozenLib
    api("net.frozenblock:frozenlib-fabric:${frozenlib_version}")

    // Simple Copper Pipes
    compileOnlyApi("maven.modrinth:simple-copper-pipes:${copperpipes_version}")

    // Mod Menu
    implementation("com.terraformersmc:modmenu:$modmenu_version")

    // Cloth Config
    implementation("me.shedaniel.cloth:cloth-config-fabric:$cloth_config_version") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "com.terraformersmc")
    }

    // TerraBlender
    compileOnly("maven.modrinth:terrablender:${terrablender_version}")

    // Biolith
    if (shouldRunBiolith)
        implementation("com.terraformersmc:biolith-fabric:${biolith_version}")
    else
        compileOnly("com.terraformersmc:biolith-fabric:${biolith_version}")

    // Sodium
    if (shouldRunSodium)
        implementation("net.caffeinemc:sodium-fabric:${sodium_version}")
    else
        compileOnly("net.caffeinemc:sodium-fabric:${sodium_version}")
}

tasks {
    processResources {
        val properties = mapOf(
            "mod_id" to mod_id,
            "version" to version,
            "protocol_version" to protocol_version,
            "minecraft_version" to "~26.2-",

            "fabric_loader_version" to ">=$min_fabric_loader_version",
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
                "**/*.cfg",
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
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }

    named<Jar>("javadocJar") {
        // created by multiloader-common via java { withJavadocJar() }
    }

    named<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
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

val jar: Jar by tasks
val sourcesJar: Jar by tasks
val javadocJar: Jar by tasks

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
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

val env: MutableMap<String, String> = System.getenv()

publishing {
    val mavenUrl = env["MAVEN_URL"]
    val mavenUsername = env["MAVEN_USERNAME"]
    val mavenPassword = env["MAVEN_PASSWORD"]

    val snapshot = mavenUrl?.contains("snapshot")

    val publishingValid = (project.name == "wilder-fabric") && !mavenUrl.isNullOrEmpty() && !mavenUsername.isNullOrEmpty() && !mavenPassword.isNullOrEmpty()

    val publishVersion = makeModrinthVersion(mod_version)
    val snapshotPublishVersion = publishVersion + if (snapshot == true) "-SNAPSHOT" else ""

    val publishGroup = rootProject.group.toString().trim(' ')

    val hash = if (grgit.branch != null && grgit.branch.current() != null) grgit.branch.current().fullName else ""

    publications {
        var publish = true
        try {
            if (publishingValid) {
                try {
                    val xml = ResourceGroovyMethods.getText(
                        URI.create("$mavenUrl/${publishGroup.replace('.', '/')}/$snapshotPublishVersion/$publishVersion.pom").toURL()
                    )
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
    properties.load(FileInputStream(rootProject.file("gradle/publishing.properties")))
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
val changelog_text = getChangelog(rootProject.file(changelog_file))

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
