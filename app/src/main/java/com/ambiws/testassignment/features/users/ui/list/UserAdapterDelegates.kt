package com.ambiws.testassignment.features.users.ui.list

import com.ambiws.testassignment.R
import com.ambiws.testassignment.databinding.ItemDividerBinding
import com.ambiws.testassignment.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object UserAdapterDelegates {

    fun userAdapterDelegate(onClick: (Long, String) -> Unit): AdapterDelegate<List<UserListItemModel>> =
        adapterDelegateViewBinding<UserItemModel, UserListItemModel, ItemUserBinding>(
            { layoutInflater, parent ->
                ItemUserBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with(binding) {
                    Glide.with(this.root)
                        .load(item.thumbnailUrl)
                        .placeholder(R.drawable.ic_image_grey)
                        .into(ivUser)
                    tvName.text = item.name
                    tvPosts.text =
                        this.root.resources.getString(R.string.posts, item.postsCount.toString())
                    this.root.setOnClickListener {
                        onClick.invoke(item.userId, item.url)
                    }
                }
            }
        }

    fun listDividerAdapterDelegate(): AdapterDelegate<List<UserListItemModel>> =
        adapterDelegateViewBinding<DividerItemModel, UserListItemModel, ItemDividerBinding>(
            { layoutInflater, parent ->
                ItemDividerBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            // Nothing to do
        }
}
