package com.synrgy7team4.feature_transfer.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentFellowAccountBankInputBinding
import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FellowAccountBankInputFragment : Fragment() {
//    private var _binding: FragmentFellowAccountBankInputBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var sharedPreferences: SharedPreferences
//    private val viewModel by viewModel<TransferViewModel>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return FragmentFellowAccountBankInputBinding.inflate(layoutInflater).also {
//            _binding = it
//        }.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val dialog = BottomSheetDialog(requireContext())
//        val showReceiverDetail = FragmentCheckReceiverDetailBinding.inflate(layoutInflater)
//
//        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)
//
//        viewModel.accountAllList.observe(viewLifecycleOwner) { accounts ->
//            accounts?.let {
//                Log.d("YourTag", "Accounts list received: $it")
//                if (it.isNotEmpty()) {
//                    val account = it.first() // If you expect a single account
//                    val bankname = sharedPreferences.getString("bankname", "") ?: ""
//                    showReceiverDetail.tvAccountNameReceiver.text = account.userName
//                    showReceiverDetail.tvBankNameAccountBankReceiver.text = "${bankname} - ${account.accountNumber}"
//                    sharedPreferences.edit().apply {
//                        putString("accountDestinationName", account.userName)
//                        putString("accountDestinationNo", account.accountNumber)
//                        // putString("accountDestinationBankName", model.id)
//                        apply()
//                    }
//                }
//            }
//        }
//
//        val spinner = binding.bankDropdown
//        val spinnerAdapter = ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.bank_list, // Use your string-array resource
//            android.R.layout.simple_spinner_item
//        )
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = spinnerAdapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                // Get the selected item
//                val selectedBank = parent.getItemAtPosition(position).toString()
//
//                // Save the selected item to SharedPreferences
//                sharedPreferences.edit().apply {
//                    putString("bankname", selectedBank)
//                    apply()
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Optionally handle cases where no selection is made
//            }
//        }
//
//
//
//
//        binding.btnNext.setOnClickListener {
//
//
//
//            val etUserName = binding.tiedtNomorRekening as EditText
//            val strUserName = etUserName.text.toString()
//            if (TextUtils.isEmpty(strUserName)) {
//                etUserName.error = "This Cant be Empty"
//                return@setOnClickListener
//            } else {
//                //sharedPreferences.edit().putString("accountDestinationName", model.accountName).apply()
//
//                Log.d("YourTag", "cek acc ${strUserName}")
//                //sharedPreferences.edit().putString("accountDestinationBankName", model.bankName).apply()
//                viewModel.cekAccountList(token,strUserName)
//
//                // if can retrive bank name and nama, it can be show on detail ..
//                dialog.setCancelable(false)
//                dialog.setContentView(showReceiverDetail.root)
//                dialog.show()
//
//
//                showReceiverDetail.btnClose.setOnClickListener {
//                    dialog.dismiss()
//                }
//                showReceiverDetail.btnNext.setOnClickListener {
//                    val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
//                    requireView().findNavController().navigate(transferInputNav)
//
//                    dialog.dismiss()
//                }
//
//                showReceiverDetail.btnSaveReceiver.setOnClickListener {
//                    viewModel.saveAccount(token, AccountRequest(strUserName))
//                    val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
//                    requireView().findNavController().navigate(transferInputNav)
//
//                    dialog.dismiss()
//                }
//
//            }
//
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}