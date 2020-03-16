package com.example.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Content
import com.example.data.entity.Issue
import com.example.data.repository.ContentRepository
import com.example.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class IssueViewModel(
    private val issueRepository: IssueRepository,
    private val contentRepository: ContentRepository,
    val filePath: String?,
    private val fileStr: String?
) : ViewModel() {
    val title = MutableLiveData("title")
    val body = MutableLiveData("body")
    val command = MutableLiveData<Command>()

    init {
        command.postValue(Command.ShowFilePath(filePath))
    }

    fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            var imageUrl = ""
            var fileUrl = ""
            if (!filePath.isNullOrEmpty() && !fileStr.isNullOrEmpty()) {
                val content = Content(
                    "furufuru",
                    fileStr,
                    null,
                    "furufuru-image-branch"
                )
                contentRepository.post(
                    content,
                    filePath.split("/").let {
                        it.last()
                    }
                )?.let {
                    fileUrl = it.content.url
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
