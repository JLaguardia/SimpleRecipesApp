package com.prismsoft.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import com.prismsoft.foody.MainViewModel
import com.prismsoft.foody.R
import com.prismsoft.foody.ui.theme.FoodyTheme
import com.prismsoft.foody.ui.views.navigation.MainNavigationUi
import com.prismsoft.foody.ui.views.navigation.NavigatorManager
import com.prismsoft.foody.util.hideSystemUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContent {
            FoodyTheme {
                MainNavigationUi(navigator = NavigatorManager(), viewModel = mainViewModel)
            }
        }

//        hideSystemUI()
//        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
//        navController = binding.navHostFragment.findNavController()
//        val appBarConfig = AppBarConfiguration(
//            setOf(
//                R.id.recipesFragment,
//                R.id.favoriteRecipesFragment,
//                R.id.foodJokeFragment,
//            )
//        )
//
//        binding.bottomNavigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController, appBarConfig)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}