package com.mertrizakaradeniz.bitcointicker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mertrizakaradeniz.bitcointicker.R
import com.mertrizakaradeniz.bitcointicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController)
    }

    private fun handleDisplayHomeAsUp() {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(controller.previousBackStackEntry != null)
        }
    }
}