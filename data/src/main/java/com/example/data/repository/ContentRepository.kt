package com.example.data.repository

import com.example.data.entity.Content
import com.example.data.entity.ContentResponse
import com.example.data.entity.Issue

interface ContentRepository {
    suspend fun post(content: Content, path: String): ContentResponse?
}
