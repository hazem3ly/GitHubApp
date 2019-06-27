package com.itg.githubapp.ui.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.extensions.loadSavedList
import com.itg.githubapp.extensions.saveToSpAndClearIfFive
import com.itg.githubapp.ui.adapters.PaginationScrollListener
import com.itg.githubapp.ui.adapters.RepositoryListAdapter
import com.itg.githubapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class HomeFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: HomeViewModel
    private val viewModelFactory: HomeViewModelFactory by instance()

    lateinit var reposAdapter: RepositoryListAdapter
    var isLoading: Boolean = false

    private var searchView: SearchView? = null

    private var isSearching: Boolean = false
    private var searchQuery: String = ""
    private var page = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)

        val searchViewItem = menu?.findItem(R.id.search)
        searchViewItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                getMoreRepos(0)
                isSearching = false
                return true
            }
        })

        searchView = searchViewItem?.actionView as SearchView
        updateAutoComplete()
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView?.clearFocus()
                query.saveToSpAndClearIfFive(requireContext())
                updateAutoComplete()
                searchRepo(query)
                isSearching = true
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            reposAdapter.removeItems()
            getMoreRepos(0)
            isSearching = false
            return@setOnCloseListener false
        }
        super.onCreateOptionsMenu(menu, inflater)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        initRecycler()

        getRepos()

    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        reposAdapter = RepositoryListAdapter({
            openRepoDetails(it)
        }, {
            openUserDetails(it)
        })
        repos_recycler?.setHasFixedSize(true)
        repos_recycler?.layoutManager = layoutManager
        repos_recycler?.adapter = reposAdapter
        repos_recycler?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                if (!isSearching)
                    getMoreItems()
                else {
                    getMoreSearching()
                }
            }
        })
    }

    private fun openUserDetails(repository: Repository) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment(repository)
        view?.findNavController()?.navigate(action)
    }

    private fun openRepoDetails(repository: Repository) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToRepoDetailsFragment()
        action.repository = repository
        view?.findNavController()?.navigate(action)
    }


    private fun updateAutoComplete() {
        val data = requireContext().loadSavedList()
        val searchAutoComplete =
            searchView?.findViewById(R.id.search_src_text) as SearchView.SearchAutoComplete
        val dataAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, data)
        searchAutoComplete.setAdapter(dataAdapter)

        searchView?.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionClick(position: Int): Boolean {
                val temp = dataAdapter.getItem(position)
                searchView?.setQuery(temp, true)
                return true
            }

            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }
        })
    }

    private fun getRepos() = launch {
        progress.visibility = View.VISIBLE
        val repos = viewModel.getRepos().await()
        repos.observe(this@HomeFragment.viewLifecycleOwner, Observer {
            progress.visibility = View.GONE
            isLoading = false

            if (it != null && it.isSuccessful) {
                updateRecycler(it.body() ?: emptyList())
            } else
                Toast.makeText(this@HomeFragment.requireContext(), "Error Loading Repos", Toast.LENGTH_SHORT).show()
        })

    }

    private fun searchRepo(query: String) = launch {
        searchQuery = query
        progress.visibility = View.VISIBLE
        reposAdapter.removeItems()
        val repos = viewModel.searchRepos(query).await()
        repos.observe(this@HomeFragment.viewLifecycleOwner, Observer {
            progress.visibility = View.GONE
            isLoading = false

            if (it != null && it.isSuccessful) {
                updateRecycler(it.body()?.items ?: emptyList())
            } else
                Toast.makeText(this@HomeFragment.requireContext(), "Error Loading Repos", Toast.LENGTH_SHORT).show()
        })
    }


    fun getMoreItems() {
        getMoreRepos(reposAdapter.getLastItemId())
    }

    private fun getMoreSearching() = launch {
        viewModel.searchRepos(searchQuery, page++).await()
    }

    private fun getMoreRepos(since: Int = 0) = launch {
        viewModel.getRepos(since).await()
    }

    private fun updateRecycler(list: List<Repository>) {
        reposAdapter.addList(list)
    }


}
