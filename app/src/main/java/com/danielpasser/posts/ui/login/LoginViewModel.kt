package com.danielpasser.posts.ui.login

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
class LoginViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    val login: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String> = mutableStateOf("")
    fun login(email:String,password:String) {
        viewModelScope.launch {
            try {
                loading.value=true
                val result = repository.login(email, password)
                if(result.errors==null)
                    login.value=true
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