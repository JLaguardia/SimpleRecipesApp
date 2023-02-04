package com.prismsoft.foody.ui.views.navigation

import com.prismsoft.foody.ui.navigation.NavBarItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigatorManager {

    private val _sharedFlow = MutableSharedFlow<NavigatorState>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    sealed class NavigatorState {

        data class NavigateAction(val action: Pair<NavBarItem, String?>): NavigatorState()
        object NavigateBack: NavigatorState()
    }

    fun navigateTo(target: NavBarItem, params: String? = null){
        val suffix = params?.let { "${target.navRoute}$it" }
        _sharedFlow.tryEmit(NavigatorState.NavigateAction(target to suffix))
    }

    fun navigateBack(){
        _sharedFlow.tryEmit(NavigatorState.NavigateBack)
    }

//    fun navigateTo(target: NavItem, params: String? = null){
//        val suffix = params?.let { "${target.linkArgPrefix}$it" }
//        _sharedFlow.tryEmit(target to suffix)
//    }
}