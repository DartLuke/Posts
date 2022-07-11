package com.danielpasser.posts.ui.components


import androidx.compose.foundation.layout.*

import androidx.compose.material.*


import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle

import kotlinx.coroutines.*



@Composable
fun ErorrSnackbar(
    snackbarHostState: SnackbarHostState
){
    Box(
        modifier = Modifier.fillMaxSize()) {

        SnackbarHost(

            hostState = snackbarHostState,

            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ){
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel?: "",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(snackbarHostState.currentSnackbarData?.message?: "")
                }
            }
        )
    }
}