package com.synrgy7team4.feature_transfer.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding

class CheckReceiverDetailFragment : Fragment() {
    private var _binding: FragmentCheckReceiverDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}