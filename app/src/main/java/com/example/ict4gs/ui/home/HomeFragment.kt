package com.example.ict4gs.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
//import com.example.ict4gs.PostDetailActivity
import com.example.ict4gs.R
import com.example.ict4gs.ui.theme.Color4
import com.example.ict4gs.ui.theme.Color5
import com.example.ict4gs.ui.theme.Color7
import com.example.ict4gs.ui.theme.Color8
import com.example.ict4gs.ui.theme.ICT4GSTheme

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        return ComposeView(requireContext()).apply {
            setContent {
                ICT4GSTheme {
                    HomeScreen()
                }
            }
        }
    }
}



@Composable

fun HomeScreen() {

        val context = LocalContext.current
        var selectedPost by remember { mutableStateOf<ForumPost?>(null) }

        val events = listOf(
            Event(
                title = stringResource(R.string.event1_title),
                date = stringResource(R.string.event1_date),
                description = stringResource(R.string.event1_description)
            ),
            Event(
                title = stringResource(R.string.event2_title),
                date = stringResource(R.string.event2_date),
                description = stringResource(R.string.event2_description)
            )
        )
        val forum1Title = stringResource(R.string.forum1_title)
        val forum1Content = stringResource(R.string.forum1_content)
        val forum2Title = stringResource(R.string.forum2_title)
        val forum2Content = stringResource(R.string.forum2_content)
        var forumPosts by remember {
            mutableStateOf(
                listOf(
                    ForumPost(forum1Title, forum1Content),
                    ForumPost(forum2Title, forum2Content)
                )
            )
        }

        var showDialog by remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Title block for Events
            item {
                TitleBlock(title = stringResource(R.string.events))
            }

            items(events) { event ->
                EventCard(event)
            }

            // Spacer
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Title block for Forum
            item {
                TitleBlock(title = stringResource(R.string.forum))
            }

            items(forumPosts) { post ->
                ForumCard(post) {
                    selectedPost = post
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.write_new_post))
                }
            }
        }

        // Alert Dialog for selected post
        selectedPost?.let { post ->
            AlertDialog(
                onDismissRequest = { selectedPost = null },
                confirmButton = {
                    TextButton(onClick = { selectedPost = null }) {
                        Text(stringResource(R.string.close))
                    }
                },
                title = { Text(post.title) },
                text = { Text(post.content) }
            )
        }
    }

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.1.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color5)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(event.title, style = MaterialTheme.typography.titleSmall)
            Text(event.date, style = MaterialTheme.typography.bodySmall)
            Text(event.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ForumCard(post: ForumPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.1.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color5)

    ) {
        Column(modifier = Modifier.padding(13.dp)) {
            Text(post.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Composable
fun NewPostDialog(onDismiss: () -> Unit, onPost: (String, String) -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    onPost(title, content)
                } else {
                    Toast.makeText(context, R.string.please_fill_all, Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(stringResource(R.string.post))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text(stringResource(R.string.new_forum_post))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.post_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(stringResource(R.string.new_forum_post)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
@Composable
fun TitleBlock(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(Color7) // Color4 can be a different color for the title block
            .padding(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}