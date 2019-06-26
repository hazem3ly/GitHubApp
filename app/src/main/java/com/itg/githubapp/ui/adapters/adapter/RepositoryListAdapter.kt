package com.itg.githubapp.ui.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.Repository

class RepositoryListAdapter(val onItemClicked: (repo: Repository) -> Unit) :
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
        holder.bind(list[position]) {
            onItemClicked(it)
        }
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

    abstract class PaginationScrollListener
    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
        (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        abstract fun isLoading(): Boolean

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }
            }
        }

        abstract fun loadMoreItems()
    }

}