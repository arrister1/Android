package com.synrgy7team4.feature_auth.presentation.onBoarding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deepLinkUri = Uri.parse("app://com.example.app/auth/onBoarding")

        AnimatorSet().apply {
            playSequentially(playMoveScaling(), playFadeAnimation())
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    // pastikan view tidak null sebelum memanggil requireView()
                    if (view != null) {
                        view.findNavController().navigate(deepLinkUri)
                    }
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