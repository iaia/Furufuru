package com.example.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Issue
import com.example.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueViewModel(
    private val issueRepository: IssueRepository
) : ViewModel() {
    val title = MutableLiveData("title")
    val body = MutableLiveData("body")

    fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            val issue = Issue("title", "body")
            issueRepository.post(issue)
        }
    }
}
