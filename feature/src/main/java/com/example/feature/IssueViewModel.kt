package com.example.feature

import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Content
import com.example.data.entity.Issue
import com.example.data.repository.ContentRepository
import com.example.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.security.MessageDigest


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
            if (!filePath.isNullOrEmpty() && !fileStr.isNullOrEmpty()) {
                val content = Content(
                    "furufuru",
                    fileStr,
                    "3d677692c18a07ca16916f7863c302399b25280f",
                    "furufuru-image-branch"
                )
                contentRepository.post(
                    content,
                    filePath.split("/").let {
                        it.last()
                    }
                )?.let {
                    imageUrl = it.content.downloadUrl
                }
            }
            val issue = Issue(
                title.value ?: return@launch,
                "${body.value}\n\n![furufuru](${imageUrl})"
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
