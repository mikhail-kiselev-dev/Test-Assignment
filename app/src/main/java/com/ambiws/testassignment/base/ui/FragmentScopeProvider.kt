package com.ambiws.testassignment.base.ui

import org.koin.core.component.KoinComponent
import org.koin.core.scope.Scope

class FragmentScopeProvider : KoinComponent {
    private var realScope: Scope? = null

    val scope: Scope
        get() = realScope
            ?: throw IllegalStateException("Attempting to call scope while fragment is already detached / has not been attached yet")

    fun onAttach(scopeDefinition: ScopeDefinition) {
        createScopeFromDefinition(scopeDefinition)
        realScope = scopeDefinition.scope
    }

    fun bindViewModel(viewModel: BaseViewModel) {
        viewModel.bindFragmentScope(
            realScope ?: throw NullPointerException("scope can't be null in bindViewModel")
        )
    }

    fun unbindViewModel(viewModel: BaseViewModel) {
        viewModel.unbindFragmentScope(
            realScope ?: throw NullPointerException("scope can't be null in unbindViewModel")
        )
    }

    fun onDetach(scopeDefinition: ScopeDefinition) {
        if (scopeDefinition.closeOnDestroy) {
            realScope?.also {
                it.close()
                getKoin().deleteScope(it.id)
            }
        }
        realScope = null
    }

    private fun createScopeFromDefinition(definition: ScopeDefinition) {
        val scopesList = mutableListOf<Scope>()
        var currentScopeDefinition = definition.parentScopeDefinition.invoke()
        while (currentScopeDefinition != null) {
            scopesList.add(currentScopeDefinition.scope)
            currentScopeDefinition = currentScopeDefinition.parentScopeDefinition.invoke()
        }

        definition.scope.linkTo(*scopesList.toTypedArray())
    }
}
