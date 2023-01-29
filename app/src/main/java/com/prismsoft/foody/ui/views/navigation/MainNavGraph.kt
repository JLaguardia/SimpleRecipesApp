package com.prismsoft.foody.ui.views.navigation

import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prismsoft.foody.MainViewModel
import com.prismsoft.foody.ui.navigation.NavBarItem
import com.prismsoft.foody.ui.views.recipes.RecipesView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.plus

fun buildNavGraph(
    builder: NavGraphBuilder,
    navigator: NavigatorManager,
    viewModel: MainViewModel
) {
    val scope = CoroutineScope(Dispatchers.IO) + Job()
    with(builder) {
        /**
         * Recipe list home
         */
        composable(NavBarItem.Recipes.navRoute) {
            RecipesView(viewModel, navigator, LocalContext.current)
                .RecipesList()
//            HomeUi(navigator)
        }

        /**
         * View Favorites
         */
        composable(NavBarItem.Favorites.navRoute) {
//            val items = remember {
//                mutableStateListOf<MacroDay?>(null)
//            }
//            if (items.any { it == null }) {
//                LoadingUi()
//            } else {
//                ViewDataSummaryUi(
//                    items.mapNotNull { it },
//                    onItemClick = { navigator.navigateTo(NavItems.Edit, it.id) },
//                    onItemRemoved = { viewModel.deleteDay(it) }
//                )
//            }
//            LaunchedEffect("history") {
//                viewModel.getAllHistory().collectLatest {
//                    with(items) {
//                        clear()
//                        addAll(it.map { it.toMacroDay() })
//                    }
//                }
//
//            }
        }

        composable(route = NavBarItem.Joke.navRoute){
            Text(text = "boop")
        }

        /**
         * Edit / add an item
         */
//        composable(
//            route = NavItems.Edit.navRouteBuilder,
//            arguments = listOf(navArgument(name = "dayId") {
//                type = NavType.StringType
//                nullable = true
//                defaultValue = null
//            })
//        ) { entry ->
//            val id = entry.arguments?.getString("dayId")
//            Log.w(TAG, "id: $id")
//            var day by remember {
//                mutableStateOf(
//                    if (id == null) {
//                        MacroDay()
//                    } else {
//                        null
//                    }
//                )
//            }
//            when (val state = day) {
//                is MacroDay -> {
//                    AddMacroView(macroDay = state) { macroDay ->
//                        scope.launch(Dispatchers.Main) {
//                            withContext(Dispatchers.Default) {
//                                macroDay?.let { viewModel.saveMacro(it) }
//                            }
//
//                            navigator.navigateTo(NavBarItem.View)
//                        }
//                    }.EditMacro()
//                }
//                else -> {
//                    LoadingUi()
//                    LaunchedEffect(Unit) {
//                        viewModel.getMacroDayById(id).collectLatest {
//                            day = it?.toMacroDay() ?: MacroDay()
//                        }
//                    }
//                }
//            }
//        }
//
//        composable(NavBarItem.Settings.navRouteBuilder) {
//            SettingsUi()
//        }
    }
}