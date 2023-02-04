package com.prismsoft.foody.ui.views.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prismsoft.foody.MainViewModel
import com.prismsoft.foody.ui.navigation.NavBarItem
import kotlinx.coroutines.flow.collectLatest

val TAG = "MainNavigationUi"

@Composable
fun MainNavigationUi(
    navigator: NavigatorManager,
    viewModel: MainViewModel
){
    val navController = rememberNavController()
    val barCompose: @Composable () -> Unit = { BarItemsUi(navController) }
    val content: @Composable (PaddingValues) -> Unit = { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavBarItem.Recipes.navRoute,
            modifier = Modifier.padding(innerPadding)
        ) { buildNavGraph(
            builder = this,
            navigator = navigator,
            viewModel = viewModel
        ) }
    }

    LaunchedEffect(key1 = "Navigation"){
        navigator.sharedFlow.collectLatest {state ->
            when(state){
                NavigatorManager.NavigatorState.NavigateBack -> navController.popBackStack()
                is NavigatorManager.NavigatorState.NavigateAction -> navController.nav(state)
            }

        }
    }

    BottomScaffold(bottomBar = barCompose, content = content)
}

private fun NavController.nav(state: NavigatorManager.NavigatorState.NavigateAction){
    val action = state.action
    val route = "${action.first.navRoute}${action.second ?: ""}"
    Log.i(TAG, "MainNavigationUi: route: $route")
    navigate(route)
}
@Composable
fun BottomScaffold(bottomBar: @Composable () -> Unit, content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun BarItemsUi(
    navController: NavController
) {
    val items = listOf(
        NavBarItem.Recipes,
        NavBarItem.Favorites,
        NavBarItem.Joke
    )

    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigation(){

}