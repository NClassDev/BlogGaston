package com.example.firebasecursogaston.domain.home

import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Resource<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)
}