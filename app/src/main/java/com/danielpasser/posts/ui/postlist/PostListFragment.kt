package com.danielpasser.posts.ui.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.danielpasser.posts.model.PostCodable
import com.danielpasser.posts.ui.BaseFragment
import com.danielpasser.posts.ui.components.ListItem
import com.danielpasser.posts.ui.components.ProgressBar
import com.danielpasser.posts.ui.editpost.EditPostFragment
import com.danielpasser.posts.utils.EditAddState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment() {
    private val viewModel: PostListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        ComposeView(requireContext()).apply {

            setContent {
                val posts = viewModel.posts.value
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = CenterVertically
                        )
                        {
                            Text("Posts", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = { addPost() },
                                shape = CircleShape,

                                ) {
                                Text(text = "Add")
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        PostLists(posts = posts,
                            onClick = { post -> showPost(post) },
                            onClickDelete = { post -> viewModel.deletePost(post.id) },
                            onClickEdit = { post -> editPost(post) }

                        )
                    }
                    ProgressBar(viewModel.loading.value)
                    showSnackBar(viewModel.error.value)
                }
            }
        }

    private fun addPost() {
        val nav = PostListFragmentDirections.actionPostListFragmentToEditPostFragment(
            PostCodable(),
            EditAddState.ADD
        )
        findNavController().navigate(nav)
    }

    private fun editPost(post: PostCodable) {
        val nav = PostListFragmentDirections.actionPostListFragmentToEditPostFragment(
            post,
            EditAddState.EDIT
        )
        findNavController().navigate(nav)
    }

    private fun showPost(post: PostCodable) {
        val nav = PostListFragmentDirections.toPostFragment(post)
        findNavController().navigate(nav)
    }
}

@Composable
fun PostLists(
    posts: List<PostCodable>,
    onClick: (PostCodable) -> Unit,
    onClickDelete: (PostCodable) -> Unit,
    onClickEdit: (PostCodable) -> Unit
) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(posts)

        { _, post ->
            ListItem(title = "Title",
                text = post.text.toString(),
                onClick = { onClick.invoke(post) },
                onClickDelete = { onClickDelete.invoke(post) },
                onClickEdit = { onClickEdit.invoke(post) })
        }
    }
}

