package com.danielpasser.posts.ui.signingup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ErorrSnackbar
import com.danielpasser.posts.ui.components.ProgressBar
import com.danielpasser.posts.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SigningUpFragment : BaseFragment() {
    private val modifier = Modifier
        .padding(
            start = 6.dp,
            end = 6.dp,
            bottom = 6.dp,
            top = 6.dp,
        )
        .fillMaxWidth()

    val viewModel: SigningUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        ComposeView(requireContext()).apply {
            setContent {
                var email by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }

                var repeatPassword by remember {
                    mutableStateOf("")
                }


                if (viewModel.login.value) loginSuccess()
                val error = viewModel.error.value



                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Signing Up",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 6.dp,
                                    end = 6.dp,
                                    bottom = 24.dp,
                                    top = 6.dp,
                                )
                        )
                        OutlinedTextField(
                            value = email,
                            label = { Text(text = "Email") },
                            onValueChange = {
                                email = it
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = modifier
                        )

                        OutlinedTextField(
                            value = password,
                            label = { Text(text = "Password") },
                            onValueChange = {
                                password = it
                            },
                            maxLines = 1,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = modifier
                        )

                        OutlinedTextField(
                            value = repeatPassword,
                            label = { Text(text = "Repeat Password") },
                            onValueChange = {
                                repeatPassword = it
                            },
                            maxLines = 1,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = modifier
                        )

                        Button(
                            onClick = { register(email, password, repeatPassword) },
                            modifier = modifier
                        ) {
                            Text(text = "Register")

                        }
                        ClickableText(
                            text = AnnotatedString("Already have an account?"),
                            onClick = { login() },
                            style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                            modifier = modifier
                        )
                    }
                    ProgressBar(visible = viewModel.loading.value)
                    showSnackBar(viewModel.error.value)
                }
            }
        }

    private fun login() {
        val nav=SigningUpFragmentDirections.toLoginFragment()
        findNavController().navigate(nav)
    }

    private fun register(email: String, password: String, repeatPassword: String) {

        viewModel.register(email, password, repeatPassword)
    }


    private fun loginSuccess() {
        val nav=SigningUpFragmentDirections.toPostListFragment()
        findNavController().navigate(nav)
    }
}