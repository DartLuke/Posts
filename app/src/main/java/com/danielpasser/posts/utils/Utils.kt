package com.danielpasser.posts.utils

import android.util.Patterns

object Utils {
    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}