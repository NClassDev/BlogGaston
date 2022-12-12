package com.example.firebasecursogaston.domain.home

import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.data.model.Post
import com.example.firebasecursogaston.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()

    override suspend fun registerLikeButtonState(postId: String, liked: Boolean) = dataSource.registerLikeButtonState(postId, liked)
}