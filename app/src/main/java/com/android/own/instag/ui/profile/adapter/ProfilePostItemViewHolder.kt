package com.android.own.instag.ui.profile.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.own.instag.R
import com.android.own.instag.data.model.ProfilePost
import com.android.own.instag.di.component.ViewHolderComponent
import com.android.own.instag.ui.base.BaseItemViewHolder
import com.android.own.instag.utils.common.GlideHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_view_profile_post.view.*

class ProfilePostItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<ProfilePost, ProfilePostItemViewModel>(
        R.layout.item_view_profile_post,
        parent
    ) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()


        viewModel.imageDetail.observe(this, Observer {
            it?.run {
                val glideRequest = Glide
                    .with(itemView.ivProfilePost.context)
                    .load(GlideHelper.getProtectedUrl(it.url, it.headers))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected))
                glideRequest.into(itemView.ivProfilePost)
            }
        })

    }

    override fun setupView(view: View) {

    }
}