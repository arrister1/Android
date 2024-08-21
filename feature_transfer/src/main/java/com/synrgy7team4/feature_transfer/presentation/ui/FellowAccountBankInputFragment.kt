package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentFellowAccountBankInputBinding

class FellowAccountBankInputFragment : Fragment() {
    private var _binding: FragmentFellowAccountBankInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFellowAccountBankInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = BottomSheetDialog(requireContext())

        val showReceiverDetail = FragmentCheckReceiverDetailBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            dialog.setCancelable(false)

            dialog.setContentView(showReceiverDetail.root)

            dialog.show()
        }

        showReceiverDetail.btnNext.setOnClickListener {
            val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
            requireView().findNavController().navigate(transferInputNav)

            dialog.dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}