package com.synrgy7team4.feature_auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentOnBoardingBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        binding.btnPunya.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }

        binding.btnBlumPunya.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_inputEmailFragment)
        }
    }

    private fun setupAccessibility() {
        binding.apply {
            imageView2.contentDescription = getString(R.string.logo_dari_lumi)
            textViewWelcome.contentDescription = getString(R.string.selamat_datang)
            btnPunya.contentDescription = getString(R.string.sudah_punya_rekening)
            btnBlumPunya.contentDescription = getString(R.string.belum_punya_rekening)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}