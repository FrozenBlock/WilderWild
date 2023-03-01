buildscript {
	repositories {
		gradlePluginPortal()
	}
	dependencies {
		classpath("org.kohsuke:github-api:1.313")
	}
}

import java.nio.file.Files

extra {
	val properties = Properties()
	properties.load(FileInputStream(file("gradle/publishing.properties")))
	properties.forEach((a, b) -> set(a, b))

	modrinth_version = makeModrinthVersion(mod_version)
	display_name = makeName(mod_version)

	changelog_text = getChangelog(file(changelog_file))
}

fun makeName(version: String): String {
	return "${version} (${minecraft_version})"
}

fun makeModrinthVersion(version: String): String {
	return "$version-mc${minecraft_version}"
}

fun getChangelog(changelogFile: File): String {
	val text = Files.readString(changelogFile.toPath())
	val split = text.split("-----------------")
	if (split.length != 2)
		throw IllegalStateException("Malformed changelog")
	return split[1].trim()
}

curseforge {
	val token = System.getenv("CURSEFORGE_TOKEN")
	apiKey = token == null || token.isEmpty() ? "unset" : token
	val gameVersion = curseforge_minecraft_version != "null" ? curseforge_minecraft_version : minecraft_version
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