package com.android.own.instag.di.component

import com.android.own.instag.di.ViewModelScope
import com.android.own.instag.ui.home.posts.PostItemViewHolder
import com.android.own.instag.di.module.ViewHolderModule
import com.android.own.instag.ui.profile.adapter.ProfilePostItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {


    fun inject(viewHolder: PostItemViewHolder)

    fun inject(viewHolder: ProfilePostItemViewHolder)
}