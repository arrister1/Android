package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentFellowAccountBankInputBinding


class FellowAccountBankInputFragment : Fragment() {
    private var _binding: FragmentFellowAccountBankInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

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
            val etUserName = binding.tiedtNomorRekening as EditText
            val strUserName = etUserName.text.toString()

            if (TextUtils.isEmpty(strUserName)) {
                etUserName.error = "This Cant be Empty"
                return@setOnClickListener
            } else {
                sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
                //sharedPreferences.edit().putString("accountDestinationName", model.accountName).apply()
                sharedPreferences.edit().putString("accountDestinationNo", strUserName).apply()
                //sharedPreferences.edit().putString("accountDestinationBankName", model.bankName).apply()

                // if can retrive bank name and nama, it can be show on detail ..
                dialog.setCancelable(false)
                dialog.setContentView(showReceiverDetail.root)
                dialog.show()
            }

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