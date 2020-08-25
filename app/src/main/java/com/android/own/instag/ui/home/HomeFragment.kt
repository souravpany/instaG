package com.android.own.instag.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.own.instag.R
import com.android.own.instag.di.component.FragmentComponent
import com.android.own.instag.ui.base.BaseFragment
import com.android.own.instag.ui.home.posts.PostsAdapter
import com.android.own.instag.ui.main.MainSharedViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var postsAdapter: PostsAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.posts.observe(this, Observer {
            it.data?.run { postsAdapter.appendData(this) }
        })

        mainSharedViewModel.newPost.observe(this, Observer {
            it.getIfNotHandled()?.run { viewModel.onNewPost(this) }
        })

        viewModel.refreshPosts.observe(this, Observer {
            it.data?.run {
                postsAdapter.updateList(this)
                rvPosts.scrollToPosition(0)
            }
        })
    }

    override fun setupView(view: View) {
        rvPosts.apply {
            layoutManager = linearLayoutManager
            adapter = postsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.run {
                        if (this is LinearLayoutManager
                            && itemCount > 0
                            && itemCount == findLastVisibleItemPosition() + 1
                        ) viewModel.onLoadMore()
                    }
                }
            })
        }
    }

}