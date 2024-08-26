package com.synrgy7team4.feature_transfer.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferDetailBinding

class CheckReceiverDetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckReceiverDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckReceiverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}