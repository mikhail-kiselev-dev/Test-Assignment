package com.ambiws.testassignment.base.ui

import org.koin.androidx.scope.fragmentScope
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

interface ScopeDefinitionProvider {
    val scopeDefinition: ScopeDefinition
}

sealed class ScopeDefinition(
    val scope: Scope,
    val closeOnDestroy: Boolean,
    val parentScopeDefinition: () -> ScopeDefinition?
) {
    class Fragment(
        fragment: androidx.fragment.app.Fragment,
        parentScopeDefinition: () -> ScopeDefinition?
    ) : ScopeDefinition(
        scope = fragment.fragmentScope(),
        closeOnDestroy = true,
        parentScopeDefinition = parentScopeDefinition
    )

    class Empty(scopeId: String, parentScopeDefinition: () -> ScopeDefinition?) :
        ScopeDefinition(
            scope = emptyScope(scopeId),
            closeOnDestroy = true,
            parentScopeDefinition = parentScopeDefinition
        )

    @Suppress("unused")
    class CustomScope(
        scope: Scope,
        closeOnDestroy: Boolean,
        parentScopeDefinition: () -> ScopeDefinition?
    ) : ScopeDefinition(
        scope = scope,
        closeOnDestroy = closeOnDestroy,
        parentScopeDefinition = parentScopeDefinition
    )
}

val EMPTY_SCOPE_NAME = named("EmptyScope")

fun emptyScope(scopeId: String) =
    (object : KoinComponent {}).getKoin().createScope(scopeId, EMPTY_SCOPE_NAME)
