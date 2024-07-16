package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy7team4.feature_auth.databinding.FragmentInputEmailBinding

class InputEmailFragment : Fragment() {
    private val binding by lazy { FragmentInputEmailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
}