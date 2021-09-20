package dev.iaiabot.furufuru.feature.ui.issue

import androidx.lifecycle.*
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal abstract class IssueViewModel : ViewModel() {
    abstract val title: LiveData<String>
    abstract val body: LiveData<String>
    abstract val userName: LiveData<String>
    abstract val imageStrBase64: LiveData<String?>
    abstract val labels: LiveData<List<String>>
    abstract val command: LiveData<Command>
    abstract val nowSending: LiveData<Boolean>

    abstract fun onTitleChange(title: String)
    abstract fun onBodyChange(body: String)
    abstract fun onUserNameChange(userName: String)
    abstract fun onCheckedChangeLabel(isChecked: Boolean, label: String)
    abstract fun post()
}

internal class IssueViewModelImpl(
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val loadUserNameUseCase: LoadUserNameUseCase,
    private val githubSettings: GithubSettings,
    private val postIssueUseCase: PostIssueUseCase,
    getScreenShotUseCase: GetScreenShotUseCase,
) : IssueViewModel() {
    override val title = MutableLiveData("")
    override val body = MutableLiveData("")
    override val userName = MutableLiveData("")
    override val imageStrBase64 = getScreenShotUseCase().asLiveData()
    override val command = MutableLiveData<Command>()
    override val nowSending = MutableLiveData(false)
    override val labels = liveData {
        emit(githubSettings.labels.toList())
    }
    private val selectedLabels = mutableListOf<String>()
    private var newUserName: String? = null

    init {
        viewModelScope.launch {
            userName.value = loadUserNameUseCase()
        }
    }

    override fun onTitleChange(title: String) {
        this.title.value = title
    }

    override fun onBodyChange(body: String) {
        this.body.value = body
    }

    override fun onUserNameChange(userName: String) {
        this.userName.value = userName
        newUserName = userName
    }

    override fun onCheckedChangeLabel(isChecked: Boolean, label: String) {
        if (isChecked) {
            selectedLabels.add(label)
        } else {
            selectedLabels.remove(label)
        }
    }

    override fun post() {
        val title = title.value ?: return
        if (title.isEmpty()) return
        val body = body.value ?: return
        val userName = newUserName ?: userName.value ?: ""

        viewModelScope.launch {
            saveUsernameUseCase(newUserName)
        }

        if (nowSending.value == true) {
            return
        }
        nowSending.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(20000)
                // postIssueUseCase(title, userName, body, selectedLabels)
                command.postValue(Command.Finish)
            } catch (e: Exception) {
                command.postValue(Command.Error(e.message ?: "error"))
            } finally {
                nowSending.postValue(false)
            }
        }
    }
}
