package com.synrgy7team4.bankingapps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.synrgy7team4.bankingapps.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        AnimatorSet().apply {
            playSequentially(playMoveScaling(), playFadeAnimation())
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    intent.putExtra("MOVE_FROM_SPLASH", true)
                    startActivity(intent)
                    finish()
                }
            })
            start()
        }


    }

    private fun playMoveScaling(): AnimatorSet {
        val imageSplash = binding.splashSlide

        val moveAnimate = ObjectAnimator.ofFloat(imageSplash, View.TRANSLATION_Y, 2800f, -100f).setDuration(7000)
        val scalingAnimate = ObjectAnimator.ofFloat(imageSplash, View.SCALE_Y, 0f, 4f).setDuration(7000)



        return AnimatorSet().apply {
            playTogether(moveAnimate, scalingAnimate)
        }
    }

    private fun playFadeAnimation(): AnimatorSet {
        val imageSplash = binding.splashSlide
        val fadeAnimate = ObjectAnimator.ofFloat(imageSplash, View.ALPHA, 1f, 0f).setDuration(3000)
        val logoFade = ObjectAnimator.ofFloat(binding.lumi, View.ALPHA, 1f, 0f).setDuration(1000)
        return AnimatorSet().apply {
            playTogether(fadeAnimate, logoFade)
        }
    }



}