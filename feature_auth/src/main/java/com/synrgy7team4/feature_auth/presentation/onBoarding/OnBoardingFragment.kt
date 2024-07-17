package com.synrgy7team4.feature_auth.presentation.onBoarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding
import com.synrgy7team4.feature_auth.presentation.login.LoginActivity


class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        playAnimation()
    }



    private fun playAnimation(){

        val logoFade = ObjectAnimator.ofFloat(binding.imageView2, View.ALPHA, 1f).apply {
            duration = 5000
        }
        val textFade = ObjectAnimator.ofFloat(binding.textViewWelcome, View.ALPHA, 1f).apply {
            duration = 1000
        }
        val btn1Fade = ObjectAnimator.ofFloat(binding.btnPunya, View.ALPHA,  1f).apply {
            duration = 1000
        }
        val btn2Fade = ObjectAnimator.ofFloat(binding.btnBlumPunya, View.ALPHA, 1f).apply {
            duration = 1000
        }

        AnimatorSet().apply {

            playSequentially(logoFade, textFade,btn1Fade,btn2Fade)
            start()
        }

    }

    private fun setupAction() {
        binding.btnPunya.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.btnBlumPunya.setOnClickListener {
//            startActivity(Intent(requireContext(), RegisterFragment::class.java))
        }
    }


}