package com.android.own.instag.ui.profile.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.android.own.instag.data.model.Post
import com.android.own.instag.data.model.ProfilePost
import com.mindorks.bootcamp.instagram.ui.base.BaseAdapter

class ProfilePostsAdapter(
    parentLifecycle: Lifecycle,
    posts: ArrayList<ProfilePost>
) : BaseAdapter<ProfilePost, ProfilePostItemViewHolder>(parentLifecycle, posts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProfilePostItemViewHolder(parent)
}