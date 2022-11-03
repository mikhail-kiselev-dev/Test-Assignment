package com.ambiws.testassignment.features.posts.ui.list

import com.ambiws.testassignment.databinding.ItemPostBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object PostAdapterDelegates {

    fun postAdapterDelegate() : AdapterDelegate<List<PostListItemModel>> =
        adapterDelegateViewBinding<PostItemModel, PostListItemModel, ItemPostBinding>(
            { layoutInflater, parent ->
                ItemPostBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with(binding) {
                    tvHeader.text = item.title
                    tvBody.text = item.body
                }
            }
        }
}
