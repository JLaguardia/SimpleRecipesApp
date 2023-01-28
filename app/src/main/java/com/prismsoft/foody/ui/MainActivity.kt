package com.prismsoft.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.prismsoft.foody.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContent {
            Text("hello back to compose")
        }
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