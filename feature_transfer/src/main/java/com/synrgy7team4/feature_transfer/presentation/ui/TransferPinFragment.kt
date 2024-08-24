package com.example.feature_transfer.presentation.ui

import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.synrgy7team4.common.databinding.PinInputBinding
import com.synrgy7team4.common.databinding.PinNumberBinding
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinBinding
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset


class TransferPinFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTransferPinBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: TransferViewModel by viewModel()

    private lateinit var pinNumberBinding : PinNumberBinding
    private lateinit var pinInputBinding : PinInputBinding

    private val numberList = ArrayList<String>()
    private var passCode = ""
    private var input1: String? = null
    private var input2: String? = null
    private var input3: String? = null
    private var input4: String? = null
    private var input5: String? = null
    private var input6: String? = null
    private lateinit var savedPin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_pin, container, false)
        _binding = FragmentTransferPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        savedPin = sharedPreferences.getString("pin", "") ?: ""

        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)

        initializeComponents()
        observeViewModel()
    }

    private fun initializeComponents() {
        binding.apply {
            pinNumberBinding.btnNum1.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum2.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum3.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum4.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum5.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum6.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum7.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum8.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum9.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnNum0.setOnClickListener(this@TransferPinFragment)
            pinNumberBinding.btnDelete.setOnClickListener(this@TransferPinFragment)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            pinNumberBinding.btnNum1.id -> addNumberToList("1")
            pinNumberBinding.btnNum2.id -> addNumberToList("2")
            pinNumberBinding.btnNum3.id -> addNumberToList("3")
            pinNumberBinding.btnNum4.id -> addNumberToList("4")
            pinNumberBinding.btnNum5.id -> addNumberToList("5")
            pinNumberBinding.btnNum6.id -> addNumberToList("6")
            pinNumberBinding.btnNum7.id -> addNumberToList("7")
            pinNumberBinding.btnNum8.id -> addNumberToList("8")
            pinNumberBinding.btnNum9.id -> addNumberToList("9")
            pinNumberBinding.btnNum0.id -> addNumberToList("0")
            pinNumberBinding.btnDelete.id -> {
                if (numberList.isNotEmpty()) {
                    numberList.removeAt(numberList.size - 1)
                    passNumber(numberList)
                }
            }
        }
    }

    private fun addNumberToList(number: String) {
        if (numberList.size < 6) {
            numberList.add(number)
            passNumber(numberList)
        }
    }

    private fun passNumber(numberList: ArrayList<String>) {
        clearPinDisplay()

        for (i in numberList.indices) {
            when (i) {
                0 -> {
                    input1 = numberList[0]
                    pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                1 -> {
                    input2 = numberList[1]
                    pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                2 -> {
                    input3 = numberList[2]
                    pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                3 -> {
                    input4 = numberList[3]
                    pinInputBinding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                4 -> {
                    input5 = numberList[4]
                    pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                5 -> {
                    input6 = numberList[5]
                    pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                    passCode = input1 + input2 + input3 + input4 + input5 + input6
                    if (passCode.length == 6) {
//                        validatePin()
                        val accountDestinationNo = sharedPreferences.getString("accountDestinationNo", "") ?: ""
                        val transferAmount = sharedPreferences.getInt("transferAmount", 0)
                        val transferDescription = sharedPreferences.getString("transferDescription", "") ?: ""
                        val bankname = sharedPreferences.getString("bankname", "") ?: ""
                        val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
                        val dateTimePlus7Hours = currentDateTime.plusHours(14)
                        val token = sharedPreferences.getString("token", "") ?: ""
                        viewModel.getUserData(token)
                        viewModel.userData.observe(viewLifecycleOwner) { user ->
                            val accnumb = user.data?.accountNumber
                            if (accnumb != null) {
                                viewModel.postTransfer(token, TransferReq(accnumb, accountDestinationNo, transferAmount, transferDescription, passCode, dateTimePlus7Hours.toString(),bankname ))

                            }

                        }

                    }
                }
            }
        }
    }

    private fun clearPinDisplay() {
        pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        pinInputBinding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
    }

    private fun validatePin() {
        if (passCode == savedPin) {
            // PIN benar, lanjutkan ke proses transaksi
        } else {
            setToast("PIN salah!")
            clearPinDisplay()
            numberList.clear()
        }
    }

    private fun observeViewModel() {
        viewModel.transferResult.observe(viewLifecycleOwner, Observer { result ->
            // Handle the transfer result
            val id = result.data?.data?.id
            sharedPreferences.edit().apply {
                putString("lastidtransaction", id)
                // putString("accountDestinationBankName", model.id)
                apply()
            }

            if (result != null) {
                val deepLinkUri = Uri.parse("app://com.example.app/trans/transDetail")
                requireView().findNavController().navigate(deepLinkUri)
            }
        })

        // Observe loading state to show/hide loading indicator
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                // Show loading indicator (e.g., ProgressBar)
            } else {
                // Hide loading indicator
            }
        })

        // Observe error state to display error messages
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                // Show the error message using a Toast, Snackbar, or Dialog
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        })
    }


    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

