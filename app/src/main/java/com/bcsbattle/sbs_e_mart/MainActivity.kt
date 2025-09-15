package com.bcsbattle.sbs_e_mart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bcsbattle.sbs_e_mart.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)

        binding.bottomNavigationView.setupWithNavController(navController)

        // Add an OnDestinationChangedListener to hide/show the bottom navigation
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Hide bottom navigation on LoginFragment and RegisterFragment
            if (destination.id == R.id.loginFragment || destination.id == R.id.signUpFragment) {
                binding.bottomNavigationView.visibility = BottomNavigationView.GONE  // Set it to GONE, not INVISIBLE
            } else {
                binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE
            }
        }
    }
}
