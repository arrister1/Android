package com.synrgy7team4.bankingapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.synrgy7team4.bankingapps.databinding.ActivityMainBinding
import com.synrgy7team4.common.NavigationHandler
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity(), NavigationHandler {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getKoin().declare(this as NavigationHandler)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun navigateToOnBoarding() {
        TODO("Not yet implemented")
    }

    override fun navigateToDashboard() {
        navController.navigate(R.id.auth_to_dashboard_navigation)
    }

    override fun navigateToMutasi() {
        TODO("Not yet implemented")
    }

    override fun navigateToTransfer() {
        TODO("Not yet implemented")
    }
}