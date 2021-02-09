package com.onionsquare.goabase.feature

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.onionsquare.goabase.R

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    override fun onBackPressed() {
        findNavController(R.id.nav_host).let { navController ->
            navController.currentDestination.let { currentDestination ->
                if (currentDestination?.id == R.id.countriesFragment) {
                    super.onBackPressed()
                } else {
                    navController.navigateUp()
                }
            }
        }
    }

}