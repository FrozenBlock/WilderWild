import com.matthewprenger.cursegradle.CurseArtifact
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import org.ajoberstar.grgit.Grgit
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties
import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.kohsuke:github-api:1.313")
    }
}

plugins {
    id("fabric-loom") version("+")
    id("io.github.juuxel.loom-quiltflower") version("+")
    id("org.ajoberstar.grgit") version("+")
    id("com.modrinth.minotaur") version("+")
    id("com.matthewprenger.cursegradle") version("+")
    `maven-publish`
    eclipse
    idea
    `java-library`
    java
}

public val minecraft_version: String by project
public val quilt_mappings: String by project
public val parchment_mappings: String by project
public val loader_version: String by project

public val mod_id: String by project
public val mod_version: String by project
public val mod_loader: String by project
public val maven_group: String by project
public val archives_base_name: String by project

public val fabric_version: String by project
public val fabric_asm_version: String by project
public val frozenlib_version: String by project

public val betterend_version: String by project
public val betternether_version: String by project
public val modmenu_version: String by project
public val cloth_config_version: String by project
public val copperpipes_version: String by project
public val nbtcrafting_version: String by project
public val terrablender_version: String by project
public val terralith_version: String by project
public val tomsstorage_version: String by project

public val sodium_version: String by project
public val iris_version: String by project
public val indium_version: String by project
public val sodium_extra_version: String by project
public val reeses_sodium_options_version: String by project
public val lithium_version: String by project
public val fastanim_version: String by project
public val ferritecore_version: String by project
public val lazydfu_version: String by project
public val starlight_version: String by project
public val entityculling_version: String by project
public val memoryleakfix_version: String by project
public val no_unused_chunks_version: String by project

base {
    archivesName.set(archives_base_name)
}

version = getVersion()
group = maven_group

public val local_frozenlib = findProject(":FrozenLib") != null
public val release = findProperty("releaseType") == "stable"

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
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            //vmArg("-Dfabric-api.datagen.strict-validation")
            vmArg("-Dfabric-api.datagen.modid=$mod_id")

            ideConfigGenerated(true)
            runDir = "build/datagen"
        }

        named("client") {
            ideConfigGenerated(false)
        }
        named("server") {
            ideConfigGenerated(false)
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
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")

        content {
            includeGroup("maven.modrinth")
        }
    }
    maven {
        url = uri("https://maven.terraformersmc.com")

        content {
            includeGroup("com.terraformersmc")
        }
    }
    maven {
        url = uri("https://maven.shedaniel.me/")
    }
    maven {
        url = uri("https://cursemaven.com")

        content {
            includeGroup("curse.maven")
        }
    }
    /*maven {
        name = "Siphalor's Maven"
        url = uri("https://maven.siphalor.de")
    }*/
    maven {
        url = uri("https://maven.flashyreese.me/releases")
    }
    maven {
        url = uri("https://maven.flashyreese.me/snapshots")
    }
    maven {
        url = uri("https://maven.minecraftforge.net/")
    }
    maven {
        url = uri("https://maven.parchmentmc.org")
    }
    maven {
        name = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
    }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(loom.layered {
        // please annoy treetrain if this doesnt work
        mappings("org.quiltmc:quilt-mappings:${minecraft_version}+build.${quilt_mappings}:intermediary-v2")
        parchment("org.parchmentmc.data:parchment-1.19.2:${parchment_mappings}@zip")
        officialMojangMappings {
            nameSyntheticMembers = false
        }
    })
    modImplementation("net.fabricmc:fabric-loader:${loader_version}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")

    // FrozenLib
    println("Using local FrozenLib: $local_frozenlib")
    if (local_frozenlib) {
        implementation(project(path = ":FrozenLib", configuration = "dev"))?.let { include(it) }
    } else {
        modImplementation("maven.modrinth:frozenlib:${frozenlib_version}")?.let { include(it) }
    }

    // CaffeineConfig
    //include(modImplementation("net.caffeinemc:mixin-config:1.0.0+1.17"))

    // Lunade
    modImplementation("maven.modrinth:simple-copper-pipes:${copperpipes_version}")

    // Mod Menu
    modImplementation("com.terraformersmc:modmenu:${modmenu_version}")

    // Cloth Config
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${cloth_config_version}") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "com.terraformersmc")
    }

    // NBT Crafting
    modImplementation("maven.modrinth:nbt-crafting:${nbtcrafting_version}")?.let { include(it) }

    // CaffeineConfig
    modImplementation("net.caffeinemc:mixin-config:1.0.0+1.17")?.let { include(it) }

    // TerraBlender
    modImplementation("com.github.glitchfiend:TerraBlender-fabric:${minecraft_version}-${terrablender_version}")

    // Sodium
    modCompileOnly("maven.modrinth:sodium:${sodium_version}")
    modCompileOnly("org.joml:joml:1.10.4")
    modCompileOnly("org.anarres:jcpp:1.4.14")

    // BetterEnd
    modCompileOnly("maven.modrinth:betterend:${betterend_version}")

    // BetterNether
    modCompileOnly("maven.modrinth:betternether:${betternether_version}")

    // only affects runClient, does not affect gradlew build.
    // add -PuseThirdPartyMods=false to not use these
    if (findProperty("useThirdPartyMods") != "false") {
        modRuntimeOnly("maven.modrinth:ferrite-core:${ferritecore_version}")
        modRuntimeOnly("maven.modrinth:lazydfu:${lazydfu_version}")
        modRuntimeOnly("maven.modrinth:starlight:${starlight_version}")
        modRuntimeOnly("maven.modrinth:lithium:${lithium_version}")
        modRuntimeOnly("maven.modrinth:fastanim:${fastanim_version}")

        modRuntimeOnly("maven.modrinth:entityculling:${entityculling_version}")
        modRuntimeOnly("maven.modrinth:memoryleakfix:${memoryleakfix_version}")
        modRuntimeOnly("maven.modrinth:no-unused-chunks:${no_unused_chunks_version}")
    }

    // only affects runClient, does not affect gradlew build.
    // add -PuseExperimentalThirdParty=true to the gradle runClient
    // command to use these
    if (findProperty("useExperimentalThirdParty") == "true") {
        modRuntimeOnly("maven.modrinth:terralith:${terralith_version}")
        modRuntimeOnly("maven.modrinth:sodium:${sodium_version}")
        modRuntimeOnly("org.joml:joml:1.10.4")
        modRuntimeOnly("org.anarres:jcpp:1.4.14")
        //modRuntimeOnly "maven.modrinth:iris:${iris_version}"
        modRuntimeOnly("maven.modrinth:indium:${indium_version}")
        modRuntimeOnly("me.flashyreese.mods:reeses-sodium-options:${reeses_sodium_options_version}") {
            exclude(group = "net.coderbot.iris_mc1_19", module = "iris")
        }
        modRuntimeOnly("me.flashyreese.mods:sodium-extra-fabric:${sodium_extra_version}")
        modRuntimeOnly("io.github.douira:glsl-transformer:0.27.0")
    }
}

quiltflower {
    quiltflowerVersion.set("1.9.0")
}

tasks {
    processResources {
        val properties = HashMap<String, Any>()
        properties["version"] = version
        properties["minecraft_version"] = minecraft_version

        properties.forEach { (a, b) -> inputs.property(a, b) }

        filesMatching("fabric.mod.json") {
            expand(properties)
        }

        val globalProperties = HashMap<String, Any>()
        globalProperties["mod_id"] = mod_id

        globalProperties.forEach { (a, b) -> inputs.property(a, b) }
    
        filesNotMatching(listOf("**/*.accesswidener", "**/*.nbt")) {
            expand(globalProperties)
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
        // Minecraft 1.18 (1.18-pre2) upwards uses Java 17.
        options.release.set(17)
        options.isFork = true
        options.isIncremental = true
    }

    withType(Test::class) {
        maxParallelForks = Runtime.getRuntime().availableProcessors().div(2)
    }
}

public val test: Task by tasks
public val runClient: Task by tasks
public val runDatagen: Task by tasks

public val remapJar: Task by tasks
public val sourcesJar: Task by tasks
public val javadocJar: Task by tasks

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
    withJavadocJar()
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName}" }
        }
    }
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

fun getVersion(): String {
    var version = "$mod_version-$mod_loader+$minecraft_version"

    if (!release) {
        version += "-unstable"
    }

    return version
}

if (!(release || System.getenv("GITHUB_ACTIONS") == "true")) {
    test.dependsOn(runDatagen)
    runClient.dependsOn(runDatagen)
}

extra {
    val properties = Properties()
    properties.load(FileInputStream(file("gradle/publishing.properties")))
    properties.forEach { (a, b) ->
        project.extra[a as String] = b as String
    }
}

public val modrinth_id: String by extra
public val curseforge_id: String by extra
public val release_type: String by extra
public val curseforge_minecraft_version: String by extra
public val changelog_file: String by extra

public val modrinth_version = makeModrinthVersion(mod_version)
public val display_name = makeName(mod_version)
public val changelog_text = getChangelog(file(changelog_file))

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

tasks {
    curseforge {
        val token = System.getenv("CURSEFORGE_TOKEN")
        apiKey = { if (token == null || token.isEmpty()) "unset" else token }
        val gameVersion = { if (curseforge_minecraft_version != "null") curseforge_minecraft_version else minecraft_version }
        project(closureOf<CurseProject> {
            id = curseforge_id
            changelog = changelog_text
            releaseType = release_type
            addGameVersion("Fabric")
            addGameVersion("Quilt")
            addGameVersion(gameVersion)
            relations(closureOf<CurseRelation> {
                requiredDependency("fabric-api")
                optionalDependency("cloth-config")
                optionalDependency("modmenu")
                optionalDependency("terrablender-fabric")
                optionalDependency("simple-copper-pipes")
                embeddedLibrary("frozenlib")
                embeddedLibrary("nbt-crafting")
            })
            remapJar.get()
            mainArtifact(file("build/libs/${remapJar.get().archiveBaseName.get()}-${version}.jar"), closureOf<CurseArtifact> {
                displayName = display_name
            })
            afterEvaluate {
                uploadTask.dependsOn(remapJar)
            }
        })
        curseGradleOptions.forgeGradleIntegration = false
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set(modrinth_id)
        versionNumber.set(modrinth_version)
        versionName.set(display_name)
        versionType.set(release_type)
        changelog.set(changelog_text)
        uploadFile.set(file("build/libs/${remapJar.get().archiveBaseName.get()}-${version}.jar"))
        gameVersions.set(listOf(minecraft_version))
        loaders.set(listOf("fabric", "quilt"))
        dependencies {
            required.project("fabric-api")
            optional.project("cloth-config")
            optional.project("modmenu")
            optional.project("simple-copper-pipes")
            embedded.project("frozenlib")
            embedded.project("nbt-crafting")
        }
    }


    val github = register("github") {
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
            ghRelease.uploadAsset(remapJar.get().archiveFile.get().getAsFile(), "application/java-archive")
            ghRelease.uploadAsset(remapSourcesJar.get().archiveFile.get().getAsFile(), "application/java-archive")
            ghRelease.uploadAsset(javadocJar.outputs.files.singleFile, "application/java-archive")
        }
    }

    register("publishMod") {
        dependsOn(github)
        dependsOn(curseforge)
        dependsOn(modrinth)
    }
}
