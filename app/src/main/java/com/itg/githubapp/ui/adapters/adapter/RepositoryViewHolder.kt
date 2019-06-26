package com.itg.githubapp.ui.adapters.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_layout_item.view.*

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val owner_image: ImageView = itemView.owner_image
    val repo_owner: TextView = itemView.repo_owner
    val repo_name: TextView = itemView.repo_name

    fun bind(item: Repository, onItemClicked: (repo: Repository) -> Unit) {
        itemView.setOnClickListener {
            onItemClicked(item)
        }
        Picasso.get().load(item.owner.avatar_url).into(owner_image)
        repo_name.text = item.name
        repo_owner.text = item.owner.login

    }

    fun resetItem() {
        owner_image.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.github_repo))
        repo_name.text = ""
        repo_owner.text = ""
    }
}