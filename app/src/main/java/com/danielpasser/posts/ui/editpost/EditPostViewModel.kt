package com.danielpasser.posts.ui.editpost

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.posts.model.CommentCodable
import com.danielpasser.posts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val close: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    fun updatePost(postId: Int, text: String) {
        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.updatePost(text, postId)
                if (result.errors == null)
                    close.value = true
            } catch (e: Exception) {
                Log.e("TEST", e.toString())
                error.value=e.toString()
            }
            finally {
                loading.value=false
            }
        }
    }

    fun addPost(text: String) {
        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.addPost(text)
                if (result.errors == null)
                    close.value = true
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