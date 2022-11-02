package com.ambiws.testassignment.features.users.ui.list

import com.ambiws.testassignment.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object UserAdapterDelegates {

    fun userAdapterDelegate(): AdapterDelegate<List<UserListItemModel>> =
        adapterDelegateViewBinding<UserItemModel, UserListItemModel, ItemUserBinding>(
            { layoutInflater, parent ->
                ItemUserBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with(binding) {
                    Glide.with(this.root).load(item.thumbnailUrl).into(ivUser)
                    tvName.text = item.name
                    tvPosts.text = "${item.name} posts count"
                }
            }
        }
}
