import org.eclipse.jgit.api.Git
import kotlin.io.path.readText

tasks {
    val changelogTask = register("createChangelog")
    changelogTask.configure {
        group = "cookies"
        enabled = true
        doLast {
            val git = Git.open(rootDir)

            val currentTag = git.tagList().call().first()
            val previousTag = git.tagList().call().let {
                if (it.size > 1) {
                    it[1].objectId
                } else {
                    git.log().call().first().toObjectId()
                }
            }

            val gitHistory = git.log().addRange(previousTag, currentTag.objectId).call()
            val changeLogBuilder = StringBuilder()
            val changeLogHeader = rootDir.toPath().resolve("gradle/CHANGELOG_HEADER.md").readText(Charsets.UTF_8)

            changeLogBuilder.append(applyPlaceholders(changeLogHeader))

            gitHistory.forEach {
                if (it.fullMessage.contains("[SKIP]")) {
                    return@forEach
                }
                changeLogBuilder.append("- ").append(it.shortMessage).append(" - @").append(it.committerIdent.name).append("\n")
            }

            rootDir.resolve("CHANGELOG.md").writeText(changeLogBuilder.toString())
        }
    }
}

fun applyPlaceholders(text: String): String {
    return text.replace("\$VERSION$", "${getRootProject().version}")
        .replace("\$projectName$", getRootProject().name)
}