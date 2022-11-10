package com.ambiws.testassignment.features.posts.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.testassignment.R
import com.ambiws.testassignment.base.list.DefaultListDiffer
import com.ambiws.testassignment.base.ui.BaseFragment
import com.ambiws.testassignment.base.ui.EmptyViewModel
import com.ambiws.testassignment.core.extensions.subscribe
import com.ambiws.testassignment.databinding.FragmentPostsBinding
import com.ambiws.testassignment.features.posts.ui.list.PostAdapterDelegates
import com.ambiws.testassignment.features.posts.ui.list.PostItemModel
import com.ambiws.testassignment.features.posts.ui.list.PostListItemModel
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PostsFragment : BaseFragment<EmptyViewModel, FragmentPostsBinding>(
    R.layout.fragment_posts,
    FragmentPostsBinding::bind
) {

    private val args: PostsFragmentArgs by navArgs()

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<PostListItemModel>(),
            PostAdapterDelegates.postAdapterDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        Glide.with(requireContext())
            .load(args.postsParams.userImage)
            .placeholder(R.drawable.placeholder)
            .into(binding.ivUserImage)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserPosts.layoutManager = layoutManager
        binding.rvUserPosts.adapter = adapter
        adapter.items = args.postsParams.posts.map {
            PostItemModel(
                title = it.title,
                body = it.body,
            )
        }
    }

    private fun setupObservers() {
        subscribe(viewModel.loadingObservable) {
            binding.loadingProgressBar.isVisible = it
        }
    }
}
