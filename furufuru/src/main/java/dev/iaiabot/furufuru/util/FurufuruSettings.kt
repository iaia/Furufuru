package dev.iaiabot.furufuru.util

class FurufuruSettings {
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
}
