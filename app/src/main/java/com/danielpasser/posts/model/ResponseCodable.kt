package com.danielpasser.posts.model

data class ResponseCodable<T>(val data:T,val errors:ErrorCodable)
