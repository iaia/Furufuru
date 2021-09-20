package dev.iaiabot.furufuru.feature.ui.issue

import androidx.lifecycle.*
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal abstract class IssueViewModel : ViewModel() {
    abstract val title: LiveData<String>
    abstract val body: LiveData<String>
    abstract val userName: LiveData<String>
    abstract val imageStrBase64: LiveData<String?>

    abstract fun onTitleChange(title: String)
    abstract fun onBodyChange(body: String)
    abstract fun onUserNameChange(userName: String)
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
    override val userName = liveData {
        emit(loadUserNameUseCase())
    }
    private var newUserName: String? = null
    override val imageStrBase64 = getScreenShotUseCase().asLiveData()
    val nowSending = MutableLiveData(false)
    private val labels = liveData {
        emit(githubSettings.labels)
    }
    private val command = MutableLiveData<Command>()
    private val selectedLabels = mutableListOf<String>()

    override fun onTitleChange(title: String) {
        this.title.value = title
    }

    override fun onBodyChange(body: String) {
        this.body.value = body
    }

    override fun onUserNameChange(userName: String) {
        newUserName = userName
    }

    fun onCheckedChangeLabel(isChecked: Boolean, label: String) {
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
        val userName = newUserName ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            saveUsernameUseCase(userName)
        }

        if (nowSending.value == true) {
            return
        }
        nowSending.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                postIssueUseCase(title, userName, body, selectedLabels)
                command.postValue(Command.Finish)
            } catch (e: Exception) {
                command.postValue(Command.Error(e.message ?: "error"))
            } finally {
                nowSending.postValue(false)
            }
        }
    }
}

// FIXME: Contract作る
internal sealed class Command {
    object Finish : Command()
    object ShowFilePath : Command()
    class Error(val errorMessage: String) : Command()
}
