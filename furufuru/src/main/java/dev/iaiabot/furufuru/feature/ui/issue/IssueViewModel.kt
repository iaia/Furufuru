package dev.iaiabot.furufuru.feature.ui.issue

import android.app.Application
import androidx.lifecycle.*
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class IssueViewModel(
    application: Application,
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val githubSettings: GithubSettings,
    private val postIssueUseCase: PostIssueUseCase,
    getScreenShotUseCase: GetScreenShotUseCase,
) : AndroidViewModel(
    application
), LifecycleObserver {
    val title = MutableLiveData("")
    val body = MutableLiveData("")
    val userName = MutableLiveData("")
    val command = MutableLiveData<Command>()
    val nowSending = MutableLiveData(false)
    val fileStr = getScreenShotUseCase().asLiveData()
    val labels = MutableLiveData<List<String>>()
    private val selectedLabels = mutableListOf<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            userName.postValue(saveUsernameUseCase.load())
        }
        viewModelScope.launch(Dispatchers.Default) {
            labels.postValue(githubSettings.labels)
        }
    }

    fun onCheckedChangeLabel(isChecked: Boolean, label: String) {
        if (isChecked) {
            selectedLabels.add(label)
        } else {
            selectedLabels.remove(label)
        }
    }

    fun post() {
        val title = title.value ?: return
        if (title.isEmpty()) return
        val body = body.value ?: return
        val userName = userName.value ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            saveUsernameUseCase.save(userName)
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
