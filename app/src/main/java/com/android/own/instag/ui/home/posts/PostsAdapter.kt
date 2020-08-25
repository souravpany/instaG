package com.android.own.instag.ui.home.posts

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.android.own.instag.data.model.Post
import com.mindorks.bootcamp.instagram.ui.base.BaseAdapter

class PostsAdapter(
    parentLifecycle: Lifecycle,
    posts: ArrayList<Post>
) : BaseAdapter<Post, PostItemViewHolder>(parentLifecycle, posts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostItemViewHolder(parent)
}