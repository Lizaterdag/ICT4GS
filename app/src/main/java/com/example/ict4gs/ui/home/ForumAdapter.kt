package com.example.ict4gs.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ict4gs.R

class ForumAdapter(
    private val posts: List<ForumPost>,
    private val onItemClick: (ForumPost) -> Unit
) : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    inner class ForumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum_post, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val post = posts[position]
        holder.titleText.text = post.title
        holder.itemView.setOnClickListener {
            onItemClick(post)
        }
    }
    override fun getItemCount(): Int = posts.size
}
