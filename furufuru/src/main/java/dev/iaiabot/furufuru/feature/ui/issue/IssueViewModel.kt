package dev.iaiabot.furufuru.feature.ui.issue

import android.app.Application
import android.text.format.DateFormat
import androidx.lifecycle.*
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.data.repository.UserRepository
import dev.iaiabot.furufuru.util.FurufuruSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

internal class IssueViewModel(
    application: Application,
    private val issueRepository: IssueRepository,
    private val contentRepository: ContentRepository,
    private val screenshotRepository: ScreenshotRepository,
    private val furufuruSettings: FurufuruSettings,
    private val userRepository: UserRepository
) : AndroidViewModel(
    application
), LifecycleObserver {
    val title = MutableLiveData("")
    val body = MutableLiveData("")
    val userName = MutableLiveData("")
    val command = MutableLiveData<Command>()
    val nowSending = MutableLiveData(false)
    val fileStr = MutableLiveData<String?>(null)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(3) { repeatNum ->
                val screenshot = screenshotRepository.get()
                if (screenshot == null) {
                    delay(1000L * repeatNum)
                    return@repeat
                }
                fileStr.postValue(screenshot)
                return@launch
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            userName.postValue(userRepository.getUserName(getApplication()))
        }
    }

    fun post() {
        val title = title.value ?: return
        if (title.isEmpty()) return
        val body = body.value ?: return
        val userName = userName.value ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveUserName(getApplication(), userName)
        }

        if (nowSending.value == true) {
            return
        }
        nowSending.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            val imageUrls = uploadImage()

            val issue = Issue(
                title,
                IssueBodyTemplate.createBody(
                    userName,
                    body,
                    imageUrls?.imageUrl,
                    imageUrls?.fileUrl
                )
            )
            issueRepository.post(issue)
            command.postValue(Command.Finish)

            nowSending.postValue(false)
        }
    }

    private suspend fun uploadImage(): ImageUrls? {
        val fileStr = fileStr.value ?: return null
        if (fileStr.isEmpty()) {
            return null
        }
        val content = Content(
            "[ci skip] Upload furufuru image",
            fileStr,
            null,
            furufuruSettings.furufuruBranch
        )

        val now = Date()
        val nowString = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        val path = "$nowString.jpg"
        contentRepository.post(
            content,
            path
        )?.let {
            return ImageUrls(
                it.content.htmlUrl,
                it.content.downloadUrl
            )
        }
        return null
    }
}

data class ImageUrls(
    val fileUrl: String,
    val imageUrl: String
)

sealed class Command {
    object Finish : Command()
    object ShowFilePath : Command()
}
