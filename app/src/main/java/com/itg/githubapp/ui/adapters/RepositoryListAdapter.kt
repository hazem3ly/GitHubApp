package com.itg.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.Repository

class RepositoryListAdapter(
    val onItemClicked: (repo: Repository) -> Unit,
    val onIconClicked: (repo: Repository) -> Unit
) :
    RecyclerView.Adapter<RepositoryViewHolder>() {

    private var list: ArrayList<Repository> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_layout_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(list[position], {
            onItemClicked(it)
        }, {
            onIconClicked(it)
        })
    }

    fun getLastItemId(): Int {
        return list.last().id
    }

    fun addList(list: List<Repository>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItems() {
        this.list.clear()
        notifyDataSetChanged()
    }
}