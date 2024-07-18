package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.synrgy7team4.feature_auth.databinding.FragmentInputPhoneNumberBinding

class InputPhoneNumberFragment : Fragment() {
    private val binding by lazy { FragmentInputPhoneNumberBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}