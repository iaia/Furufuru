package dev.iaiabot.furufuru.util

internal class GithubSettings {
    companion object {
        private const val DEFAULT_FURUFURU_BRANCH = "furufuru-image-branch"
    }

    var githubApiToken: String = ""
        private set
    var githubRepositoryOwner: String = ""
        private set
    var githubRepository: String = ""
        private set
    var furufuruBranch: String = DEFAULT_FURUFURU_BRANCH
        private set

    // TODO: Listにしたい
    val labels: MutableList<String> = mutableListOf()

    fun init(
        githubApiToken: String,
        githubReposOwner: String,
        githubRepository: String,
        furufuruBranch: String? = null
    ) {
        this.githubApiToken = githubApiToken
        this.githubRepositoryOwner = githubReposOwner
        this.githubRepository = githubRepository
        furufuruBranch?.let {
            this.furufuruBranch = it
        }
    }

    fun addLabels(labels: List<String>) {
        if (labels.isNotEmpty()) {
            this.labels.addAll(labels)
        }
    }
}
