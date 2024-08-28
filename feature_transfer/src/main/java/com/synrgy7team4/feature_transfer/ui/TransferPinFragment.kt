package com.synrgy7team4.feature_transfer.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.common.databinding.PinInputBinding
import com.synrgy7team4.common.databinding.PinNumberBinding
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinBinding
import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
class TransferPinFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentTransferPinBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<TransferViewModel>()
    private lateinit var pinNumberBinding: PinNumberBinding
    private lateinit var pinInputBinding: PinInputBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val numberList = ArrayList<String>()
    private var input1: String? = null
    private var input2: String? = null
    private var input3: String? = null
    private var input4: String? = null
    private var input5: String? = null
    private var input6: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTransferPinBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)
//
//        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
//        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)
//
//        initializeComponents()
//
//        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
//            if (result.success!!) {
//                val id = result.data?.id
//                sharedPreferences.edit().apply {
//                    putString("lastidtransaction", id)
//                    apply()
//                }
//                findNavController().navigate(R.id.action_transferPinFragment_to_transferDetailFragment)
//            } else {
//                makeToast(requireContext(), "Transfer gagal: ${result.message}")
//                clearPinDisplay()
//                numberList.clear()
//            }
//        }
//
//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            makeToast(requireContext(), "Error: ${error.message}")
//            clearPinDisplay()
//            numberList.clear()
//        }
//    }

        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)

        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)

        initializeComponents()

        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
            if (result.success == true) {
                val id = result.data?.id
                sharedPreferences.edit().apply {
                    putString("lastidtransaction", id)
                    apply()
                }
                findNavController().navigate(R.id.action_transferPinFragment_to_transferDetailFragment)
            } else {
                makeToast(requireContext(), "Transfer gagal: ${result.message}")
                clearPinDisplay()
                numberList.clear()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), "Error: ${error.message}")
            clearPinDisplay()
            numberList.clear()
        }
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
                    val passCode = input1 + input2 + input3 + input4 + input5 + input6
                    if (passCode.length == 6) {
                        initiateTransfer(passCode)
                    }
                }
            }
        }
    }

    private fun initiateTransfer(pin: String) {
        val accountDestinationNo = sharedPreferences.getString("accountDestinationNo", "") ?: ""
        val accountDestinationName = sharedPreferences.getString("accountDestinationName", "") ?: ""
        val transferAmount = sharedPreferences.getInt("transferAmount", 0)
        val transferDescription = sharedPreferences.getString("transferDescription", "") ?: ""
        val destinationBank = sharedPreferences.getString("bankname", "Lumi Bank") ?: "Lumi Bank"
        val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
        val dateTimePlus7Hours = currentDateTime.plusHours(14)

        lifecycleScope.launch {
            awaitAll(viewModel.initializeData())
            viewModel.transfer(
                pin = pin,
                accountTo = accountDestinationNo,
                amount = transferAmount,
                description = transferDescription,
                datetime = dateTimePlus7Hours.toString(),
                destinationBank = destinationBank,
            )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

