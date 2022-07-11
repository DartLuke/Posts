package com.danielpasser.posts.ui.editpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielpasser.posts.utils.EditAddState
import com.danielpasser.posts.model.PostCodable
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ProgressBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditPostFragment : BaseFragment() {
    private val safeArgs: EditPostFragmentArgs by navArgs()
    private val post: PostCodable by lazy { safeArgs.post }
    private val viewModel: EditPostViewModel by viewModels()
    private lateinit var state: EditAddState

    private val modifier = Modifier
        .padding(
            start = 6.dp,
            end = 6.dp,
            bottom = 6.dp,
            top = 6.dp,
        )
        .fillMaxWidth()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        state = safeArgs.state
        setContent {
            var title by remember {
                mutableStateOf("")
            }
            var text by remember {
                mutableStateOf(post.text)
            }
            val close = viewModel.close.value
            if (close) findNavController().popBackStack()

            Box(modifier = Modifier.fillMaxSize()) {
                Column {

                    Text(
                        text = when (state) {
                            EditAddState.EDIT -> "Post Editing"
                            EditAddState.ADD -> "Post Adding"
                        }, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = modifier

                    )
                    OutlinedTextField(
                        value = title,
                        maxLines = 1,
                        label = { Text(text = "Title") },
                        onValueChange = {
                            title = it
                        },
                        modifier = modifier

                    )

                    OutlinedTextField(
                        value = text.toString(),
                        label = { Text(text = "Text") },
                        onValueChange = {
                            text = it
                        },
                        maxLines = 6,
                        modifier = modifier
                    )

                Button(
                    onClick = { send(text) },
                    modifier = modifier
                ) {
                    Text(text = "Send")

                }
            }
                ProgressBar(viewModel.loading.value)
                showSnackBar(viewModel.error.value )
            }
        }
    }

    private fun send(text: String?) {
        when (state) {
            EditAddState.ADD -> viewModel.addPost(text.toString())
            EditAddState.EDIT -> viewModel.updatePost(text = text.toString(), postId = post.id)
        }
    }

}