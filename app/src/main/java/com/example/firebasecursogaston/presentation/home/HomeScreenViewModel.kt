package com.example.firebasecursogaston.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers

class HomeScreenViewModel(private val repo: HomeScreenRepo): ViewModel() {

    fun fetchLatestPosts() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())

        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { postList ->
            emit(postList)
        }.onFailure { throwable ->
            emit(Resource.Failure(Exception(throwable.message)))
        }
    }

    fun registerLikeButtonState(postId: String, liked: Boolean) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        kotlin.runCatching {
            repo.registerLikeButtonState(postId, liked)
        }.onSuccess {
            emit(Resource.Success(Unit))
        }.onFailure { throwable ->
            emit(Resource.Failure(Exception(throwable.message)))
        }
    }
}


class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}