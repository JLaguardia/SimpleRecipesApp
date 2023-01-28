package com.prismsoft.foody.ui.views.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prismsoft.foody.MainViewModel
import com.prismsoft.foody.ui.navigation.NavBarItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainNavigationUi(
    navigator: NavigatorManager,
    viewModel: MainViewModel
){

    val TAG = "MainNavigationUi"
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
        navigator.sharedFlow.collectLatest {

            val route = "${it.first.navRoute}${it.second ?: ""}"
            Log.i(TAG, "MainNavigationUi: route: $route")
            navController.navigate(route)
        }
    }

    BottomScaffold(bottomBar = barCompose, content = content)
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