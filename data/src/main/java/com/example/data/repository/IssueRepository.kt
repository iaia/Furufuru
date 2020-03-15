package com.example.data.repository

import com.example.data.entity.Issue

interface IssueRepository {
    suspend fun post(issue: Issue)
}
