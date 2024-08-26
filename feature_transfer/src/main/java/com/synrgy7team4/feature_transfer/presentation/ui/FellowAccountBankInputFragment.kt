package com.synrgy7team4.feature_transfer.presentation.ui.transfer

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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentFellowAccountBankInputBinding
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FellowAccountBankInputFragment : Fragment() {
    private var _binding: FragmentFellowAccountBankInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private val transferViewModel: TransferViewModel by viewModel()

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

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", "") ?: ""

        transferViewModel.accountAllList.observe(viewLifecycleOwner) { accounts ->
            accounts?.let {
                Log.d("YourTag", "Accounts list received: $it")
                if (it.isNotEmpty()) {
                    val account = it.first() // If you expect a single account
                    val bankname = sharedPreferences.getString("bankname", "") ?: ""
                    showReceiverDetail.tvAccountNameReceiver.text = account.userName
                    showReceiverDetail.tvBankNameAccountBankReceiver.text = "${bankname} - ${account.accountNumber}"
                    sharedPreferences.edit().apply {
                        putString("accountDestinationName", account.userName)
                        putString("accountDestinationNo", account.accountNumber)
                        // putString("accountDestinationBankName", model.id)
                        apply()
                    }
                }
            }
        }

        val spinner = binding.bankDropdown
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.bank_list, // Use your string-array resource
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected item
                val selectedBank = parent.getItemAtPosition(position).toString()

                // Save the selected item to SharedPreferences
                sharedPreferences.edit().apply {
                    putString("bankname", selectedBank)
                    apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optionally handle cases where no selection is made
            }
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            val accountNumber = binding.tiedtNomorRekening.text.toString()
            if(accountNumber.isNotEmpty()){
                sharedPreferences.edit().apply(){
                    putString("accountNumber", accountNumber)
                    apply()
                }
            }

            val  etUserName = binding.tiedtNomorRekening as EditText
            val strUserName = etUserName.text.toString()
            if (TextUtils.isEmpty(strUserName)) {
                etUserName.error = "This Cant be Empty"
                return@setOnClickListener
            } else {
                //sharedPreferences.edit().putString("accountDestinationName", model.accountName).apply()

                Log.d("YourTag", "cek acc ${strUserName}")
                //sharedPreferences.edit().putString("accountDestinationBankName", model.bankName).apply()
                transferViewModel.cekAccountList(token,strUserName)

                // if can retrive bank name and nama, it can be show on detail ..
                dialog.setCancelable(false)
                dialog.setContentView(showReceiverDetail.root)
                dialog.show()


                val displayAcc =  sharedPreferences.getString("bankName", null)
                val displayNum = sharedPreferences.getString("accountNumber", null)

                showReceiverDetail.tvBankNameAccountBankReceiver.text =
                    "${displayAcc} - ${displayNum}"


                showReceiverDetail.btnClose.setOnClickListener {
                    dialog.dismiss()
                }
                showReceiverDetail.btnNext.setOnClickListener {
                    val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
                    requireView().findNavController().navigate(transferInputNav)

                    dialog.dismiss()
                }

                showReceiverDetail.btnSaveReceiver.setOnClickListener {
                    transferViewModel.postAccount(token, AccountRequest(strUserName))
                    Toast.makeText(requireContext(),
                                       "Data penerima berhasil disimpan",
                                       Toast.LENGTH_SHORT
                                   ).show()

                }

            }

        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}