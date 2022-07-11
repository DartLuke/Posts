package com.danielpasser.posts.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danielpasser.posts.model.CommentCodable
import com.danielpasser.posts.model.CommentsCodable
import com.danielpasser.posts.model.PostCodable
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ListItem
import com.danielpasser.posts.ui.components.ProgressBar
import com.danielpasser.posts.ui.postlist.PostListFragmentDirections
import com.danielpasser.posts.ui.postlist.PostLists
import com.danielpasser.posts.utils.EditAddState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : BaseFragment() {
    private val safeArgs: PostFragmentArgs by navArgs()
    private val post: PostCodable by lazy { safeArgs.post }
    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        viewModel.getComments(post.id)
        setContent {

            val comments = viewModel.comments.value

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    Modifier
                        .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Title",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier

                            .padding(top = 8.dp, bottom = 8.dp)
                    )

                    Text(
                        text = post.text.toString(), modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text("Comments", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Button(
                            onClick = { addComment() },
                            shape = CircleShape,

                            ) {
                            Text(text = "Add")
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    CommentList(
                        comments = comments,
                        onClickEdit = { post -> editComment(post) },
                        onClickDelete = { post -> deleteComment(post) }
                    )
                }
                ProgressBar(viewModel.loading.value)
                showSnackBar(viewModel.error.value)
            }
        }
    }

    private fun deleteComment(comment: CommentCodable) {
        viewModel.deletePost(commentId = comment.id, postId = post.id)
    }

    private fun addComment() {
        val nav = PostFragmentDirections.toEditCommentFragment(
            CommentCodable(),
            EditAddState.ADD
        )
        findNavController().navigate(nav)
    }

    private fun editComment(comment: CommentCodable) {
        val nav = PostFragmentDirections.toEditCommentFragment(
            comment,
            EditAddState.EDIT
        )
        findNavController().navigate(nav)
    }


}

@Composable
fun CommentList(
    comments: List<CommentCodable>,
    onClickDelete: (CommentCodable) -> Unit,
    onClickEdit: (CommentCodable) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(comments)
        { _, comment ->
            ListItem(
                title = "Name",
                text = comment.text.toString(),
                onClick = { },
                onClickDelete = { onClickDelete.invoke(comment) },
                onClickEdit = { onClickEdit.invoke(comment) })
        }
    }
}

