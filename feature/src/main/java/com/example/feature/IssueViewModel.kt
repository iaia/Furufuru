package com.example.feature

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Issue
import com.example.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueViewModel(
    private val issueRepository: IssueRepository,
    val filePath: String?
) : ViewModel() {
    val title = MutableLiveData("title")
    val body = MutableLiveData("body")
    val command = MutableLiveData<Command>()

    init {
        command.postValue(Command.ShowFilePath(filePath))
    }

    fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            val issue = Issue("title", "body")
            issueRepository.post(issue)
            command.postValue(Command.Finish)
        }
    }
}

sealed class Command {
    object Finish: Command()
    class ShowFilePath(val filePath: String?): Command()
}
