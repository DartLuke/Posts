package com.danielpasser.posts.network

import com.danielpasser.posts.model.*
import retrofit2.http.*

interface Api {

    //comments
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: Int): ResponseCodable<CommentsCodable>

    @HTTP(method = "DELETE", path = "posts/{id}/comments", hasBody = true)
    suspend fun deleteComment(
        @Path("id") postId: Int,
        @Body forDelete: ForDelete
    ): ResponseCodable<Nothing>

    @HTTP(method = "PUT", path = "posts/{id}/comments", hasBody = true)
    suspend fun addComment(@Path("id") postId: Int, @Body forAdd: ForAdd): ResponseCodable<Nothing>

    @POST("posts/{id}/comments")
    suspend fun updateComment(
        @Path("id") postId: Int,
        @Body forUpdate: ForUpdate
    ): ResponseCodable<Nothing>


    //posts
    @GET("posts")
    suspend fun getPosts(): ResponseCodable<PostsCodable>

    @HTTP(method = "DELETE", path = "posts", hasBody = true)
    suspend fun deletePost(@Body forDelete: ForDelete): ResponseCodable<Nothing>

    @HTTP(method = "PUT", path = "posts", hasBody = true)
    suspend fun addPost(@Body forAdd: ForAdd): ResponseCodable<Nothing>

    @POST("posts")
    suspend fun updatePost(@Body forUpdate: ForUpdate): ResponseCodable<Nothing>

    //account
    @POST("account/signin")
    suspend fun login(@Body account: Account): ResponseCodable<Nothing>
    @POST("account")
    suspend fun register(@Body account: Account): ResponseCodable<Nothing>

}