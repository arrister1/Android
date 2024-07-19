package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentInputPhoneNumberBinding

class InputPhoneNumberFragment : Fragment() {
    private val binding by lazy { FragmentInputPhoneNumberBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            view.findNavController().navigate(R.id.action_inputPhoneNumberFragment_to_otpVerification)
        }
        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}