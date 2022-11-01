package com.ambiws.testassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.ambiws.testassignment.core.extensions.setCustomAnimations
import com.ambiws.testassignment.core.extensions.subscribe
import com.ambiws.testassignment.core.models.AnimationType
import com.ambiws.testassignment.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

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
        val destination = R.id.blankFragment
        mainViewModel.initStartDestinationBy(destination)
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
    }
}
