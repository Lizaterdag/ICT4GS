package com.example.ict4gs.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ict4gs.R
import com.example.ict4gs.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var forumAdapter: ForumAdapter


    private val forumPosts = mutableListOf<ForumPost>()
    private val events = mutableListOf<Event>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupEvents()
        setupForum()

        binding.newPostButton.setOnClickListener {
            showNewPostDialog()
        }

        return binding.root
    }

    private fun setupEvents() {
        events.add(
            Event(
                getString(R.string.event1_title),
                getString(R.string.event1_date),
                getString(R.string.event1_description)
            )
        )
        events.add(
            Event(
                getString(R.string.event2_title),
                getString(R.string.event2_date),
                getString(R.string.event2_description)
            )
        )

        val adapter = EventAdapter(events)
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventsRecyclerView.adapter = adapter
    }


    private fun setupForum() {
        forumPosts.add(
            ForumPost(
                getString(R.string.forum1_title),
                getString(R.string.forum1_content)
            )
        )
        forumPosts.add(
            ForumPost(
                getString(R.string.forum2_title),
                getString(R.string.forum2_content)
            )
        )

        forumAdapter = ForumAdapter(forumPosts) { selectedPost ->
            val intent = Intent(requireContext(), PostDetailActivity::class.java).apply {
                putExtra("title", selectedPost.title)
                putExtra("content", selectedPost.content)
            }
            startActivity(intent)
        }
        binding.forumRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.forumRecyclerView.adapter = forumAdapter
    }

    private fun showFullPostDialog(post: ForumPost) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.forum_post))
            .setMessage(post.content)
            .setPositiveButton(getString(R.string.close), null)
            .show()
    }

    private fun showNewPostDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_new_post, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.titleInput)
        val contentInput = dialogView.findViewById<EditText>(R.id.contentInput)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.new_forum_post))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.post)) { _, _ ->
                val title = titleInput.text.toString()
                val content = contentInput.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    forumPosts.add(ForumPost(title, content))
                    forumAdapter.notifyItemInserted(forumPosts.size - 1)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
