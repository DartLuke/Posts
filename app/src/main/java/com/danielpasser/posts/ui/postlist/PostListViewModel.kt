package com.danielpasser.posts.ui.postlist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.posts.model.PostCodable
import com.danielpasser.posts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val posts: MutableState<List<PostCodable>> = mutableStateOf(ArrayList())
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.getPosts()
                if(result.errors==null)  posts.value = result.data.posts
            } catch (e: Exception) {
                Log.e("TEST", e.toString())
                error.value=e.toString()
            }
            finally {
                loading.value=false
            }
        }

    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.deletePost(id)
                if(result.errors==null) getPosts()

            } catch (e: Exception) {
                Log.e("TEST", e.toString())
                error.value=e.toString()
            }
            finally {

                loading.value=false
            }
        }
    }
}