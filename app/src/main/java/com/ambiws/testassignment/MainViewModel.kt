package com.ambiws.testassignment

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ambiws.testassignment.core.models.AnimationType
import com.ambiws.testassignment.core.models.NavigationBundle
import com.ambiws.testassignment.core.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    val startDestinationLiveEvent = SingleLiveEvent<NavigationBundle>()

    fun initStartDestinationBy(
        destinationId: Int,
        bundle: Bundle? = null,
        animationType: AnimationType = AnimationType.NONE,
    ) {
        startDestinationLiveEvent.value = NavigationBundle(destinationId, bundle, animationType)
    }
}
