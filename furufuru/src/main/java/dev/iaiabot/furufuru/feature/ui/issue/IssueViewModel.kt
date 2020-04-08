package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class IssueViewModel(
    private val issueRepository: IssueRepository,
    private val contentRepository: ContentRepository,
    val fileStr: String?
) : ViewModel() {
    val title = MutableLiveData("")
    val body = MutableLiveData("")
    val command = MutableLiveData<Command>()

    fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            var imageUrl = ""
            var fileUrl = ""
            if (!fileStr.isNullOrEmpty()) {
                val content = Content(
                    "furufuru",
                    fileStr,
                    null,
                    "furufuru-image-branch"
                )

                val now = Date()
                val nowString = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
                val path = "$nowString.jpg"
                contentRepository.post(
                    content,
                    path
                )?.let {
                    fileUrl = it.content.htmlUrl
                    imageUrl = it.content.downloadUrl
                }
            }
            val issue = Issue(
                title.value ?: return@launch,
                "${body.value}\n\n![furufuru](${imageUrl})\n\n${fileUrl}"
            )
            issueRepository.post(issue)
            command.postValue(Command.Finish)
        }
    }
}

sealed class Command {
    object Finish : Command()
    class ShowFilePath(val filePath: String?) : Command()
}
