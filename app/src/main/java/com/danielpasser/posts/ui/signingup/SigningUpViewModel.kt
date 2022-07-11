package com.danielpasser.posts.ui.signingup

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.posts.repository.Repository
import com.danielpasser.posts.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigningUpViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val login: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    val loading: MutableState<Boolean> = mutableStateOf(false)
    fun register(email: String, password: String, repeatPassword: String) {

        if (check(email,password,repeatPassword))
            viewModelScope.launch {
                try {
                    loading.value = true
                    val result = repository.register(email, password)
                    loading.value = false
                    if (result.errors == null)
                        login.value = true
                } catch (e: Exception) {
                    Log.e("TEST", e.toString())
                    error.value = e.toString()
                    loading.value = false
                }
            }
    }

    private fun check(email: String, password: String, repeatPassword: String): Boolean {
        if (!Utils.isEmailValid(email)) {
            error.value = "Email is not correct"
            return false
        }
        if (password.isBlank() && repeatPassword.isBlank()) {
            error.value = "Password is empty"
            return false
        }
        if (password != repeatPassword) {
            error.value = "Passwords don't much"
            return false
        }
        return true
    }
}