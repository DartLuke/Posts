package com.danielpasser.posts.ui

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.danielpasser.posts.ui.components.ErorrSnackbar
import kotlinx.coroutines.launch

open class BaseFragment :Fragment()
{

    @Composable
    fun showSnackBar(text:String)
    {
        val snackbarHostState = remember{ SnackbarHostState() }
        if(text.isNotEmpty())

            lifecycleScope.launch {
                snackbarHostState.showSnackbar(
                    message = text,
                    actionLabel = "Hide",
                    duration = SnackbarDuration.Short
                )
            }
        ErorrSnackbar(snackbarHostState = snackbarHostState)
    }
}