package com.danielpasser.posts.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.fragment.findNavController
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val modifier = Modifier
        .padding(
            start = 6.dp,
            end = 6.dp,
            bottom = 6.dp,
            top = 6.dp,
        )
        .fillMaxWidth()

    val viewModel: LoginViewModel by viewModels()
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
                if(viewModel.login.value) loginSuccess()

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Welcome to the App",
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
                            visualTransformation = PasswordVisualTransformation(),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = modifier
                        )
                        Button(
                            onClick = { viewModel.login(email, password) },
                            modifier = modifier
                        ) {
                            Text(text = "Login")

                        }

                        ClickableText(
                            text = AnnotatedString("Signing up"),
                            onClick = { signingUp() },
                            style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                            modifier = modifier
                        )
                    }
                    ProgressBar(viewModel.loading.value)
                    showSnackBar(viewModel.error.value)
                }
            }
        }

    private fun signingUp() {
        val nav = LoginFragmentDirections.toSigningUpFragment()
        findNavController().navigate(nav)
    }

    private fun loginSuccess() {
        val nav = LoginFragmentDirections.toPostListFragment()
        findNavController().navigate(nav)
    }
}