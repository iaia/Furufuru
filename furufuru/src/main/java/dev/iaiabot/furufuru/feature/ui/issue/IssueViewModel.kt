package dev.iaiabot.furufuru.feature.ui.issue

import android.app.Application
import androidx.lifecycle.*
import dev.iaiabot.furufuru.usecase.IssueUseCase
import dev.iaiabot.furufuru.usecase.UsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class IssueViewModel(
    application: Application,
    private val issueUseCase: IssueUseCase,
    private val usernameUseCase: UsernameUseCase,
    private val githubSettings: GithubSettings
) : AndroidViewModel(
    application
), LifecycleObserver {
    val title = MutableLiveData("")
    val body = MutableLiveData("")
    val userName = MutableLiveData("")
    val command = MutableLiveData<Command>()
    val nowSending = MutableLiveData(false)
    val fileStr = MutableLiveData<String?>(null)
    val labels = MutableLiveData<List<String>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            fileStr.postValue(issueUseCase.getScreenShot())
        }
        viewModelScope.launch(Dispatchers.IO) {
            userName.postValue(usernameUseCase.load())
        }
        labels.value = githubSettings.labels
    }

    fun post() {
        val title = title.value ?: return
        if (title.isEmpty()) return
        val body = body.value ?: return
        val userName = userName.value ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            usernameUseCase.save(userName)
        }

        if (nowSending.value == true) {
            return
        }
        nowSending.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                issueUseCase.post(title, userName, body)
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
