package com.itg.githubapp.ui.fragments.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.UserDetails
import com.itg.githubapp.ui.adapters.PaginationScrollListener
import com.itg.githubapp.ui.adapters.UserDetailsAdapter
import com.itg.githubapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.user_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UserDetailsFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val viewModelFactory: UserDetailsViewModelFactory by instance()
    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_details_fragment, container, false)
    }

    var repo: Repository? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailsViewModel::class.java)

        val safeArgs = arguments?.let { UserDetailsFragmentArgs.fromBundle(it) }

        repo = safeArgs?.repository

        repo?.let { getUserDetails(it) }


    }

    var userDetails: UserDetails? = null
    private fun getUserDetails(repository: Repository) = launch {
        progress.visibility = View.VISIBLE
        val userDetail = viewModel.getUserDetails(repository).await()
        userDetail.observe(this@UserDetailsFragment.viewLifecycleOwner, Observer {
            progress.visibility = View.GONE
            if (it != null && it.isSuccessful) {
                it.body()?.let { it1 ->
                    userDetails = it1
                    getUserRepos(repository)
                }
            } else {
                Toast.makeText(this@UserDetailsFragment.requireContext(), "Error Loading Repos", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getUserRepos(repo: Repository) = launch {
        progress.visibility = View.VISIBLE
        val userRepos = viewModel.getUserRepos(repo).await()
        userRepos.observe(this@UserDetailsFragment.viewLifecycleOwner, Observer {

            progress.visibility = View.GONE
            if (it != null && it.isSuccessful) {
                it.body()?.let { it1 ->
                    userDetails?.let {
                        if (!isLoading) bindRecycler(it1 as ArrayList<RepoDetails>, userDetails!!)
                        else {
                            updateList(it1)
                            isLoading = false
                        }
                    }
                }
            } else {
                Toast.makeText(this@UserDetailsFragment.requireContext(), "Error Loading Repos", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun updateList(list: List<RepoDetails>) {
        adapter?.addList(list)
    }

    var adapter: UserDetailsAdapter? = null
    var isLoading: Boolean = false

    private fun bindRecycler(list: ArrayList<RepoDetails>, userDetails: UserDetails) {

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = UserDetailsAdapter(userDetails, list) {
            onRepoDetailsClicked(it)
        }
        user_recycler?.setHasFixedSize(true)
        user_recycler?.layoutManager = layoutManager
        user_recycler?.adapter = adapter
        user_recycler?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItems()
            }
        })

    }

    private var page = 0

    private fun getMoreItems() = launch {
        repo?.let { viewModel.getUserRepos(it, page++).await() }
    }

    fun onRepoDetailsClicked(repoDetails: RepoDetails) {
        val action =
            UserDetailsFragmentDirections.actionUserDetailsFragmentToRepoDetailsFragment()
        action.repoDetqails = repoDetails
        view?.findNavController()?.navigate(action)
    }

}
