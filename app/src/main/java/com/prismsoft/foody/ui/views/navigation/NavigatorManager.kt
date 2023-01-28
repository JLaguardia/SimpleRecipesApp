package com.prismsoft.foody.ui.views.navigation

import com.prismsoft.foody.ui.navigation.NavBarItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigatorManager {

    private val _sharedFlow = MutableSharedFlow<Pair<NavBarItem, String?>>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(target: NavBarItem, params: String? = null){
        val suffix = params?.let { "${target.navRoute}$it" }
        _sharedFlow.tryEmit(target to suffix)
    }

//    fun navigateTo(target: NavItem, params: String? = null){
//        val suffix = params?.let { "${target.linkArgPrefix}$it" }
//        _sharedFlow.tryEmit(target to suffix)
//    }
}