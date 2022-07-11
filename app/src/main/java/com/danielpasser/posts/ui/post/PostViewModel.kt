package com.danielpasser.posts.ui.post

import android.util.Log
import android.util.MalformedJsonException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.posts.model.CommentCodable
import com.danielpasser.posts.repository.Repository
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: Repository) :ViewModel() {


    val comments: MutableState<List<CommentCodable>> = mutableStateOf(ArrayList())
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    fun deletePost(commentId: Int, postId: Int) {
        viewModelScope.launch {
            try {
                loading.value=false
                val result = repository.deleteComment(postId=postId, commentId = commentId)
                if(result.errors==null) getComments(postId)

            } catch (e: java.lang.Exception) {
                Log.e("TEST", e.toString())
                error.value=e.toString()
            }
            finally {
                loading.value=false
            }
        }

    }

    fun getComments(postId:Int) {

        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.getComments(postId)
                comments.value = result.data.comments
            }

            catch (e: Exception) {
                Log.e("TEST", e.toString())
                error.value=e.toString()
            }
            finally {
                loading.value=false
            }
        }
    }
}