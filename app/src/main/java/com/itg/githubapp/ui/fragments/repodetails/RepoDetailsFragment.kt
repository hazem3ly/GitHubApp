package com.itg.githubapp.ui.fragments.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.itg.githubapp.R
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.ui.activities.MainActivity
import com.itg.githubapp.ui.base.ScopedFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.repo_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class RepoDetailsFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val viewModelFactory: ReposDetailsViewModelFactory by instance()
    private lateinit var viewModel: RepoDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_details_fragment, container, false)
    }

    var repo: Repository? = null
    var repoDetails: RepoDetails? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoDetailsViewModel::class.java)

        val safeArgs = arguments?.let { RepoDetailsFragmentArgs.fromBundle(it) }


        repo = safeArgs?.repository
        repoDetails = safeArgs?.repoDetqails

        if (repo != null) {
            (activity as MainActivity).toolbar.title = "${repo?.name}"
            repo?.let { getRepoDetails(it) }
        }

        if (repoDetails != null) {
            (activity as MainActivity).toolbar.title = "${repoDetails?.name}"
            bindData(repoDetails)
        }

        user_image?.setOnClickListener {
            onUserDetailsClicked()
        }

    }

    private fun getRepoDetails(repository: Repository) = launch {
        //        progress.visibility = View.VISIBLE
        val repos = viewModel.getRepoDetails(repository).await()
        repos.observe(this@RepoDetailsFragment.viewLifecycleOwner, Observer {
            //            progress.visibility = View.GONE

            if (it != null && it.isSuccessful) {
                bindData(it.body())
            } else
                Toast.makeText(
                    this@RepoDetailsFragment.requireContext(),
                    "Error Loading Repos",
                    Toast.LENGTH_SHORT
                ).show()
        })

    }

    private fun bindData(repoDetails: RepoDetails?) {
        repoDetails?.let {
            repo_name.text = it.name
            description.text = it.description
            user_name.text = it.owner.login
            forks_count.text = "${it.forks_count}"
            language.text = "${it.language}"
            default_branch.text = "${it.default_branch}"
            Picasso.get().load(it.owner.avatar_url).into(user_image)

        }
    }

    fun onUserDetailsClicked() {
        repo?.let {
            val action =
                RepoDetailsFragmentDirections.actionRepoDetailsFragmentToUserDetailsFragment(repo!!)
            view?.findNavController()?.navigate(action)
        }

        if (repoDetails != null) {
            view?.findNavController()?.navigateUp()
        }

    }

}
