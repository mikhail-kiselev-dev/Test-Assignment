package com.ambiws.testassignment.features.users.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.testassignment.R
import com.ambiws.testassignment.base.list.DefaultListDiffer
import com.ambiws.testassignment.base.ui.BaseFragment
import com.ambiws.testassignment.core.extensions.subscribe
import com.ambiws.testassignment.databinding.FragmentUsersBinding
import com.ambiws.testassignment.features.users.ui.list.UserAdapterDelegates
import com.ambiws.testassignment.features.users.ui.list.UserListItemModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UsersFragment : BaseFragment<UsersViewModel, FragmentUsersBinding>(
    R.layout.fragment_users,
    FragmentUsersBinding::bind
) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<UserListItemModel>(),
            UserAdapterDelegates.userAdapterDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListeners()
        setupObservers()
    }

    private fun setupUi() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
    }

    private fun setupListeners() {

    }

    private fun setupObservers() {
        subscribe(viewModel.users) {
            adapter.setItems(it)
        }
    }
}
