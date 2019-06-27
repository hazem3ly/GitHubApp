package com.itg.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.UserDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.*

class UserDetailsAdapter(
    val userDetails: UserDetails,
    val userRepos: ArrayList<RepoDetails>, val onRepoClicked: (repo: RepoDetails) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER = 0
    private val ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER else ITEM
    }

    override fun getItemCount(): Int {
        return userRepos.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == HEADER) {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_layout,
                    parent,
                    false
                )
            )
        } else {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_layout,
                    parent,
                    false
                )
            )
        }
    }

    fun addList(list: List<RepoDetails>) {
        this.userRepos.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(userDetails)
        }

        if (holder is ItemViewHolder) {
            holder.bind(userRepos[position - 1]){
                onRepoClicked(it)
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user_image: ImageView = itemView.user_image
        val user_name: TextView = itemView.user_name
        val email_txt: TextView = itemView.email_txt
        val followers_txt: TextView = itemView.followers_txt
        val following_txt: TextView = itemView.following_txt
        fun bind(item: UserDetails) {
            Picasso.get().load(item.avatar_url).into(user_image)
            user_name.text = item.login
            email_txt.text = item.email
            followers_txt.text = item.followers.toString()
            following_txt.text = item.following.toString()
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val owner_image: ImageView = itemView.owner_image
        val repo_owner: TextView = itemView.repo_owner
        val repo_name: TextView = itemView.repo_name
        fun bind(item: RepoDetails, onItemClicked: (repo: RepoDetails) -> Unit) {
            itemView.setOnClickListener {
                onItemClicked(item)
            }
            Picasso.get().load(item.owner.avatar_url).into(owner_image)
            repo_name.text = item.name
            repo_owner.text = item.owner.login
        }
    }


}