package com.example.firebasecursogaston

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.firebasecursogaston.core.hide
import com.example.firebasecursogaston.core.show
import com.example.firebasecursogaston.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {

                R.id.loginFragment -> {
                    binding.bottomNavigation.hide()
                }
                R.id.registerFragment -> {
                    binding.bottomNavigation.hide()
                }
                else -> {
                    binding.bottomNavigation.show()
                }
            }
        }
    }
}