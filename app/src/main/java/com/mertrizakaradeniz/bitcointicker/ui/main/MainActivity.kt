package com.mertrizakaradeniz.bitcointicker.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mertrizakaradeniz.bitcointicker.R
import com.mertrizakaradeniz.bitcointicker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        handleDisplayHomeAsUp()
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.coinsFragment,
                R.id.coinDetailFragment,
                R.id.favouriteCoinsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun handleDisplayHomeAsUp() {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(controller.previousBackStackEntry != null)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideProgressBar() {
        binding.pbCoinList.visibility = View.INVISIBLE
    }

    fun showProgressBar() {
        binding.pbCoinList.visibility = View.VISIBLE
    }

}