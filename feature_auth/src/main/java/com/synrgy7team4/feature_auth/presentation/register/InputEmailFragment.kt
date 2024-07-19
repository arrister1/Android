package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentInputEmailBinding

class InputEmailFragment : Fragment() {
    private val binding by lazy { FragmentInputEmailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            view.findNavController().navigate(R.id.action_inputEmailFragment_to_inputPhoneNumberFragment)
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}