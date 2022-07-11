package com.danielpasser.posts.ui.editcomment

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.posts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCommentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val close: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    fun updateComment(postId: Int, commentId: Int, text: String) {
        viewModelScope.launch {
            try {
                loading.value = true
                val result =
                    repository.updateComment(postId = postId, text = text, commentId = commentId)
                if (result.errors == null)
                    close.value = true
            } catch (e: Exception) {
                Log.e("TEST", e.toString())
                error.value = e.toString()
            } finally {
                loading.value = false
            }
        }
    }

    fun addComment(text: String, postId: Int) {
        viewModelScope.launch {
            try {
                loading.value = true
                val result = repository.addComment(postId, text)
                if (result.errors == null)
                    close.value = true
            } catch (e: Exception) {
                error.value = e.toString()
                Log.e("TEST", e.toString())
            } finally {
                loading.value = false
            }
        }
    }
}