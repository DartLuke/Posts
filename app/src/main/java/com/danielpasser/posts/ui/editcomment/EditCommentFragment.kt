package com.danielpasser.posts.ui.editcomment

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielpasser.posts.model.CommentCodable
import com.danielpasser.posts.utils.EditAddState
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCommentFragment : BaseFragment() {
    private val safeArgs: EditCommentFragmentArgs by navArgs()
    private val comment: CommentCodable by lazy { safeArgs.comment }
    private val viewModel: EditCommentViewModel by viewModels()
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
            var text by remember {
                mutableStateOf(comment.text)
            }
            if (viewModel.close.value) findNavController().popBackStack()
            Box(modifier = Modifier.fillMaxSize()) {
                Column{


                        Text(
                            text = when (state) {
                                EditAddState.EDIT -> "Post Editing"
                                EditAddState.ADD -> "Post Adding"
                            }, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = modifier

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
                ProgressBar(visible = viewModel.loading.value)
                showSnackBar(viewModel.error.value)
            }
        }
    }

    private fun send(text: String?) {
        when (state) {
            EditAddState.ADD -> viewModel.addComment(text.toString(), postId = safeArgs.postId)
            EditAddState.EDIT -> viewModel.updateComment(
                text = text.toString(),
                postId = safeArgs.postId,
                commentId = comment.id
            )
        }
    }
}