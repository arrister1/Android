package com.synrgy7team4.bankingapps

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commitNow
import com.synrgy7team4.bankingapps.databinding.ActivityMainBinding
import com.synrgy7team4.feature_auth.presentation.onBoarding.OnBoardingFragment
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent.getBooleanExtra("MOVE_FROM_SPLASH", true)

        if (intent) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnBoardingFragment())
                .commit()

        }



        /*setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_feature_auth) as NavHostFragment
        val navController = navHostFragment.navController

        // Navigate to the main destination
        navController.navigate(com.synrgy7team4.feature_auth.R.id.fotoKtpFragment)*/

    }





}