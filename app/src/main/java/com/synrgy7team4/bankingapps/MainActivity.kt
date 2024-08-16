package com.synrgy7team4.bankingapps

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.synrgy7team4.common.SharedPrefHelper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_feature_auth) as NavHostFragment
        val navController = navHostFragment.navController

        val splashUri = Uri.parse("app://com.example.app/auth/splashScreen")
        val homeUri = Uri.parse("app://com.example.app/dashboard/home")

        if (sharedPrefHelper.getJwtToken() == null) {
            navController.navigate(splashUri)
        } else {
            navController.navigate(homeUri)
        }
    }
}