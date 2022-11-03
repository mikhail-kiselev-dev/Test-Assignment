package com.ambiws.testassignment.base.ui

import android.net.Uri
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.ambiws.testassignment.base.navigation.NavigableViewModel
import com.ambiws.testassignment.base.navigation.NavigationCommand
import com.ambiws.testassignment.core.network.error.RetrofitCallError
import com.ambiws.testassignment.core.utils.ExceptionParser
import com.ambiws.testassignment.core.utils.SingleLiveEvent
import com.ambiws.testassignment.core.utils.loge
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import org.koin.core.scope.KoinScopeComponent
import org.koin.core.scope.Scope
import org.koin.core.scope.getScopeId
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), NavigableViewModel, KoinScopeComponent {

    private val immutableLiveDataError =
        "Not using createMutableLiveData() or createSingleLiveData() to create live data"
    private val immutableStateFlowError = "StateFlow is immutable"

    override val scope: Scope
        get() = _scope
            ?: throw IllegalStateException("Attempting to call scope after ViewModel's clear")

    val loadingObservable = MutableLiveData(false)

    val currentDestination = MutableStateFlow<NavDestination?>(null)

    val navigationCommand: LiveData<NavigationCommand> = SingleLiveEvent<NavigationCommand>()

    val defaultErrorLiveData: LiveData<String?> = MutableLiveData()

    val networkErrorLiveData: LiveData<String?> = MutableLiveData()

    protected val ioContext = Dispatchers.IO

    protected val mainContext = Dispatchers.Main

    private val exceptionParser: ExceptionParser by inject()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        sendError(throwable)
        loge(throwable.localizedMessage)
    }

    private var _scope: Scope? = null

    init {
        @Suppress("LeakingThis")
        _scope = getKoin().createScope(scopeId = getScopeId(), qualifier = EMPTY_SCOPE_NAME)
    }

    override fun onCleared() {
        super.onCleared()
        _scope?.close()
    }

    open fun bindFragmentScope(fragmentScope: Scope) {
        (_scope ?: throw NullPointerException("scope can't be null")).linkTo(fragmentScope)
    }

    override fun navigate(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras?,
        hideKeyboard: Boolean,
        navOptions: NavOptions?
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.To(
                direction,
                navigatorExtras,
                hideKeyboard,
                navOptions
            )
        )
    }

    fun unbindFragmentScope(fragmentScope: Scope) {
        _scope?.unlink(fragmentScope)
    }

    fun navigateToUri(
        uri: Uri,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(NavigationCommand.ToUri(uri, hideKeyboard))
    }

    fun navigateBack(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(NavigationCommand.Back(hideKeyboard))
    }

    fun navigateBackToStart(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(NavigationCommand.BackToStart(hideKeyboard))
    }

    fun navigateChild(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras? = null,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.To(
                    direction,
                    navigatorExtras,
                    hideKeyboard
                )
            )
        )
    }

    fun navigateChild(
        command: NavigationCommand
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                command
            )
        )
    }

    fun navigateChildToUri(
        uri: Uri,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.ToUri(
                    uri, hideKeyboard
                )
            )
        )
    }

    fun navigateChildBack(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.Back(
                    hideKeyboard
                )
            )
        )
    }

    fun navigateChildBackToStart(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.BackToStart(
                    hideKeyboard
                )
            )
        )
    }

    protected fun sendError(throwable: Throwable) {
        when (throwable) {
            is RetrofitCallError -> {
                networkErrorLiveData.postNewValue(exceptionParser.parseError(throwable))
            }
            else -> {
                defaultErrorLiveData.postNewValue(exceptionParser.parseError(throwable))
            }
        }
    }

    protected fun launch(
        dispatcher: CoroutineContext = mainContext,
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(dispatcher + errorHandler) {
            this.block()
        }
    }

    @MainThread
    protected fun <T : Any?> LiveData<T>.setNewValue(newValue: T) {
        when (this) {
            is MutableLiveData -> this.value = newValue
            else -> throw Exception(immutableLiveDataError)
        }
    }

    @MainThread
    protected fun <T : Any?> Flow<T>.setNewValue(newValue: T) {
        when (this) {
            is MutableStateFlow -> this.value = newValue
            is MutableSharedFlow -> this.tryEmit(newValue)
            else -> throw Exception(immutableLiveDataError)
        }
    }

    @MainThread
    protected fun <T : Any?> LiveData<T?>.setNewValueAndNullify(newValue: T) {
        when (this) {
            is MutableLiveData -> {
                this.value = newValue
                this.value = null
            }
            else -> throw Exception(immutableStateFlowError)
        }
    }

    @MainThread
    protected fun <T : Any?> StateFlow<T?>.setNewValueAndNullify(newValue: T) {
        when (this) {
            is MutableStateFlow -> {
                this.value = newValue
                this.value = null
            }
            else -> throw Exception(immutableStateFlowError)
        }
    }

    @MainThread
    protected fun <T : Any?> LiveData<T>.postNewValue(newValue: T) {
        when (this) {
            is MutableLiveData -> this.postValue(newValue)
            else -> throw Exception(immutableLiveDataError)
        }
    }
}
