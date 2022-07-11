package com.danielpasser.posts.repository

import com.danielpasser.posts.network.Api
import com.danielpasser.posts.model.Account
import com.danielpasser.posts.model.ForAdd
import com.danielpasser.posts.model.ForDelete
import com.danielpasser.posts.model.ForUpdate
import javax.inject.Inject

class Repository @Inject constructor(private val retrofit: Api) {
    suspend fun getPosts() = retrofit.getPosts()
    suspend fun deletePost(postId: Int) = retrofit.deletePost(ForDelete(postId))
    suspend fun addPost(text: String) = retrofit.addPost(ForAdd(text))
    suspend fun updatePost(text: String, postId: Int) = retrofit.updatePost(ForUpdate(postId, text))

    suspend fun getComments(postId: Int) = retrofit.getComments(postId)
    suspend fun addComment(postId: Int, text: String) =
        retrofit.addComment(postId = postId, ForAdd(text))

    suspend fun deleteComment(postId: Int, commentId: Int) =
        retrofit.deleteComment(postId = postId, ForDelete(commentId))

    suspend fun updateComment(postId: Int, commentId: Int, text: String) =
        retrofit.updateComment(postId = postId, ForUpdate(commentId, text))

    suspend fun login(email:String,password:String)=retrofit.login(Account(email, password))
    suspend fun register(email:String,password:String)=retrofit.register(Account(email, password))
}