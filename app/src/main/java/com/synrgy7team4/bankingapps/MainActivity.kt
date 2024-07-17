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


    }





}