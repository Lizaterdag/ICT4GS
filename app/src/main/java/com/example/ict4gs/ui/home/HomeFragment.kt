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
        events.add(Event("Autism Awareness Day", "10-05-2025", "Join us for workshops and talks."))
        events.add(Event("Parent Support Group", "05-06-2025", "Monthly meetup for parents."))

        val adapter = EventAdapter(events)
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventsRecyclerView.adapter = adapter
    }

    private fun setupForum() {
        forumPosts.add(ForumPost( "Best learning strategies?", "What are some effective learning strategies for autistic students?"))
        forumPosts.add(ForumPost( "Any recommended therapists?", "I think my child should see a therapist bla bal alb"))

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
            .setTitle("Forum Post")
            .setMessage(post.content)
            .setPositiveButton("Close", null)
            .show()
    }

    private fun showNewPostDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_new_post, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.titleInput)
        val contentInput = dialogView.findViewById<EditText>(R.id.contentInput)

        AlertDialog.Builder(requireContext())
            .setTitle("New Forum Post")
            .setView(dialogView)
            .setPositiveButton("Post") { _, _ ->
                val title = titleInput.text.toString()
                val content = contentInput.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    forumPosts.add(ForumPost(title, content))
                    forumAdapter.notifyItemInserted(forumPosts.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
