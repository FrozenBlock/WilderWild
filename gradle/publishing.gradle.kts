buildscript {
	repositories {
		gradlePluginPortal()
	}
	dependencies {
		classpath("org.kohsuke:github-api:1.313")
	}
}

public val mod_verison: String by rootProject
public val version: String by rootProject
public val minecraft_version: String by rootProject

import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties

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

curseforge {
	val token = System.getenv("CURSEFORGE_TOKEN")
	apiKey = { if (token == null || token.isEmpty()) invoke("unset") else invoke(token) }
	val gameVersion = { if (curseforge_minecraft_version != "null") invoke(curseforge_minecraft_version) else invoke(minecraft_version) }
	project {
		id = curseforge_id
		changelog = changelog_text
		releaseType = release_type
		addGameVersion("Fabric")
		addGameVersion("Quilt")
		addGameVersion(gameVersion)
		relations {
			requiredDependency("fabric-api")
			optionalDependency("cloth-config")
			optionalDependency("modmenu")
			optionalDependency("terrablender-fabric")
			optionalDependency("simple-copper-pipes")
			embeddedLibrary("frozenlib")
			embeddedLibrary("nbt-crafting")
		}
		mainArtifact(file("build/libs/${remapJar.archiveBaseName.get()}-${version}.jar")) {
			displayName = display_name
		}
		afterEvaluate {
			uploadTask.dependsOn(remapJar)
		}
	}
	curseGradleOptions.forgeGradleIntegration = false
}

modrinth {
	token = System.getenv("MODRINTH_TOKEN")
	projectId = modrinth_id
	versionNumber = modrinth_version
	versionName = display_name
	versionType = release_type
	changelog = changelog_text
	uploadFile = file("build/libs/${remapJar.archiveBaseName.get()}-${version}.jar")
	gameVersions = [minecraft_version]
	loaders = ["fabric", "quilt"]
	dependencies {
		required.project("fabric-api")
		optional.project("cloth-config")
		optional.project("modmenu")
		optional.project("simple-copper-pipes")
		embedded.project("frozenlib")
		embedded.project("nbt-crafting")
	}
}

fun getBranch(): String {
	val env = System.getenv()
	if (env.GITHUB_REF) {
		val branch = env.GITHUB_REF
		return branch.substring(branch.lastIndexOf("/") + 1)
	}

	if (grgit == null) {
		return "unknown"
	}

	val branch = grgit.branch.current().name
	return branch.substring(branch.lastIndexOf("/") + 1)
}

import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub

tasks {
	register("github") {
		dependsOn(remapJar)
		val env = System.getenv()
		onlyIf {
			env.GITHUB_TOKEN
		}

		doLast {
			val github = GitHub.connectUsingOAuth(env.GITHUB_TOKEN as String)
			val repository = github.getRepository(env.GITHUB_REPOSITORY)

			val releaseBuilder = GHReleaseBuilder(repository, "${makeModrinthVersion(mod_version)}" as String)
			releaseBuilder.name("${makeName(mod_version)}")
			releaseBuilder.body(changelog_text)
			releaseBuilder.commitish(getBranch())
			releaseBuilder.prerelease(release_type != "release")

			val ghRelease = releaseBuilder.create()
			ghRelease.uploadAsset(remapJar.archiveFile.get().getAsFile(), "application/java-archive")
			ghRelease.uploadAsset(remapSourcesJar.archiveFile.get().getAsFile(), "application/java-archive")
			ghRelease.uploadAsset(javadocJar.archiveFile.get().getAsFile(), "application/java-archive")
		}
	}

	register("publishMod") {
		dependsOn(github)
		dependsOn(tasks.getByName("curseforge"))
		dependsOn(tasks.getByName("modrinth"))
	}
}