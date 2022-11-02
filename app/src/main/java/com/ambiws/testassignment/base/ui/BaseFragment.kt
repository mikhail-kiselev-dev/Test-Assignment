package com.ambiws.testassignment.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ambiws.testassignment.MainActivity
import com.ambiws.testassignment.MainViewModel
import com.ambiws.testassignment.base.navigation.NavigationCommand
import com.ambiws.testassignment.base.navigation.NavigationCommandHandlerImpl
import com.ambiws.testassignment.core.extensions.*
import com.ambiws.testassignment.core.utils.viewBinding
import com.ambiws.testassignment.core.view.CustomSnackbar
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.scope.KoinScopeComponent
import org.koin.core.scope.Scope
import org.koin.core.scope.getScopeId
import org.koin.core.scope.inject
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val layoutId: Int,
    factory: (View) -> VB
) : Fragment(layoutId), KoinScopeComponent, ScopeDefinitionProvider {

    /**
     * Usually u should not override this value, override [scopeDefinition] instead
     */
    override val scope: Scope
        get() = fragmentScopeProvider.scope

    override val scopeDefinition: ScopeDefinition = ScopeDefinition.Empty(getScopeId()) {
        (requestNotHostParentFragment() as? ScopeDefinitionProvider)?.scopeDefinition
    }

    val binding by viewBinding<VB>(factory)

    protected lateinit var baseActivity: MainActivity

    protected open val useTopSystemInset: Boolean = true

    protected open val useBottomSystemInset: Boolean = false

    protected open val navigationCommandHandler =
        NavigationCommandHandlerImpl(navControllerDefinition = { findNavController() })

    protected open val viewModel: VM by lazy {
        scope.getViewModel(
            owner = getVMOwnerDefinition(),
            clazz = getViewModelKClass(),
            parameters = getParameters()
        )
    }

    protected val mainViewModel: MainViewModel by sharedViewModel()

    private val customSnackbar: CustomSnackbar by inject()

    private val fragmentScopeProvider = FragmentScopeProvider()

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as MainActivity
        fragmentScopeProvider.onAttach(scopeDefinition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentScopeProvider.bindViewModel(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        view.applySystemWindowInsetsToMargin(top = useTopSystemInset, bottom = useBottomSystemInset)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentScopeProvider.onDetach(scopeDefinition = scopeDefinition)
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentScopeProvider.unbindViewModel(viewModel)
    }

    open fun getViewModelScope(): ViewModelScope = ViewModelScope.Self

    open fun getParameters(): ParametersDefinition = {
        emptyParametersHolder()
    }

    @CallSuper
    open fun observeLiveData() {
        subscribe(viewModel.defaultErrorLiveData) { message ->
            message?.let {
                onError(it)
                viewModel.defaultErrorLiveData.mutable().value = null
            }
        }
        subscribe(viewModel.networkErrorLiveData) { message ->
            message?.let {
                customSnackbar.showCustomSnackBar(requireActivity().getRootView(), message)
                viewModel.networkErrorLiveData.mutable().value = null
            }
        }
        subscribe(viewModel.navigationCommand, ::navigate)
    }

    protected open fun onError(errorMessage: String) {
        showToast(errorMessage)
    }

    protected open fun navigate(navCommand: NavigationCommand) {
        navigationCommandHandler.handle(requireActivity(), navCommand)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getViewModelKClass(): KClass<VM> {
        val actualClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return actualClass.kotlin
    }

    private fun getVMOwnerDefinition(): () -> ViewModelOwner {
        return when (val type = getViewModelScope()) {
            is ViewModelScope.Activity -> requireActivity().asOwnerDefinition()
            is ViewModelScope.Parent -> type.parentClass?.let {
                getParentFragmentByClass(it).asOwnerDefinition()
            } ?: getParentFragment(true)?.asOwnerDefinition() ?: this.asOwnerDefinition()
            else -> this.asOwnerDefinition()
        }
    }

    sealed class ViewModelScope {
        object Self : ViewModelScope()
        data class Parent(val parentClass: Class<out Fragment>? = null) : ViewModelScope()
        object Activity : ViewModelScope()
    }
}
