package com.synrgy7team4.feature_auth.presentation.onBoarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
   

    private val binding by lazy { FragmentOnBoardingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        playAnimation()
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAccessibility()

        binding.btnBlumPunya.setOnClickListener {
            val deepLinkUriRegis = Uri.parse("app://com.example.app/auth/inputEmail")
            requireView().findNavController().navigate(deepLinkUriRegis)
        }
        binding.btnPunya.setOnClickListener {
            val deepLinkUri = Uri.parse("app://com.example.app/auth/login")
            requireView().findNavController().navigate(deepLinkUri)        }



    }

    private fun setupAccessibility() {
        binding.apply {
            imageView2.contentDescription = getString(R.string.logo_dari_lumi)
            textViewWelcome.contentDescription = getString(R.string.teks_selamat_datang)
            btnPunya.contentDescription = getString(R.string.tombol_sudah_punya_rekening)
            btnBlumPunya.contentDescription = getString(R.string.tombol_belum_punya_rekening)
        }
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

       
}