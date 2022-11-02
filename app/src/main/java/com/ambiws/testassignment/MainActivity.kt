package com.ambiws.testassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.whenStarted
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ambiws.testassignment.base.navigation.NavigationCommandHandlerImpl
import com.ambiws.testassignment.core.extensions.setCustomAnimations
import com.ambiws.testassignment.core.extensions.subscribe
import com.ambiws.testassignment.core.models.AnimationType
import com.ambiws.testassignment.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    private val navigationCommandHandler: NavigationCommandHandlerImpl by lazy {
        NavigationCommandHandlerImpl(
            navControllerDefinition = { binding.navContainer.findNavController() }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        initViewBinding()
        observe()
        initNavigation()
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun observe() {
        subscribe(mainViewModel.startDestinationLiveEvent) { destinationValue ->
            initNavigationGraph(
                destinationValue.destinationId,
                destinationValue.bundle,
                destinationValue.animation
            )
        }
    }

    private fun initNavigation() {
        val container = supportFragmentManager.findFragmentById(R.id.navContainer)
        if (container != null) {
            onNavGraphInited()
            return
        } else {
            val destination = R.id.usersFragment
            mainViewModel.initStartDestinationBy(destination)
        }
    }

    private fun onNavGraphInited() {
        lifecycle.coroutineScope.launch {
            val fragment = supportFragmentManager.findFragmentById(R.id.navContainer)

            fragment?.lifecycle?.whenStarted {
                fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
                    mainViewModel.currentDestination.value = destination
                }
            }
        }
        subscribe(mainViewModel.navigationCommand) {
            navigationCommandHandler.handle(this, it)
        }
    }

    private fun initNavigationGraph(
        startDestinationId: Int,
        startDestinationBundle: Bundle?,
        animation: AnimationType
    ) {
        val navHostFragment = NavHostFragment()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(animation)
            .replace(R.id.navContainer, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()

        val graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.main_navigation)
                .apply {
                    setStartDestination(startDestinationId)
                }

        navHostFragment.navController.setGraph(graph, startDestinationBundle)
        onNavGraphInited()
    }
}
