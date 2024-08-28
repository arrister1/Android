package com.synrgy7team4.feature_transfer.ui.fromQR

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
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinFromQRBinding
import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
class TransferPinFromQRFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTransferPinFromQRBinding? = null
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
        return FragmentTransferPinFromQRBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)

        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)

        initializeComponents()

        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
            if (result.success!!) {
                val id = result.data?.id
                sharedPreferences.edit().apply {
                    putString("lastidtransactionFromQR", id)
                    apply()
                }
                findNavController().navigate(R.id.action_transferPinFromQRFragment_to_transferDetailFromQrFragment)
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
            pinNumberBinding.btnNum1.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum2.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum3.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum4.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum5.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum6.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum7.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum8.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum9.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnNum0.setOnClickListener(this@TransferPinFromQRFragment)
            pinNumberBinding.btnDelete.setOnClickListener(this@TransferPinFromQRFragment)
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
        val accountDestinationNo = sharedPreferences.getString("accountDestinationNoFromQR", "") ?: ""
        val transferAmount = sharedPreferences.getInt("transferAmountFromQR", 0)
        val transferDescription = sharedPreferences.getString("transferDescriptionFromQR", "") ?: ""
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
                destinationBank = "Lumi Bank"
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



//package com.synrgy7team4.feature_transfer.ui.fromQR
//
//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.edit
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.synrgy7team4.common.databinding.PinInputBinding
//import com.synrgy7team4.common.databinding.PinNumberBinding
//import com.synrgy7team4.common.makeToast
//import com.synrgy7team4.feature_transfer.R
//import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinFromQRBinding
//import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
//import kotlinx.coroutines.awaitAll
//import kotlinx.coroutines.launch
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import java.time.LocalDateTime
//import java.time.ZoneOffset
//
//class TransferPinFromQRFragment : Fragment(), View.OnClickListener {
//
//    private var _binding: FragmentTransferPinFromQRBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel: TransferViewModel by viewModel()
//    private lateinit var pinNumberBinding: PinNumberBinding
//    private lateinit var pinInputBinding: PinInputBinding
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var sharedPreferencesRegister: SharedPreferences
//
//    private val numberList = ArrayList<String>()
//    private var input = Array(6) { "" }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentTransferPinFromQRBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)
//        sharedPreferencesRegister = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
//
//        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
//        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)
//
//        initializeComponents()
//        setupObservers()
//    }
//
//    private fun setupObservers() {
//        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
//            val id = result.data?.id
//            sharedPreferences.edit {
//                putString("lastidtransactionFromQr", id)
//            }
//            findNavController().navigate(R.id.action_transferPinFromQRFragment_to_transferDetailFromQrFragment)
//        }
//
//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            makeToast(requireContext(), error.message)
//        }
//    }
//
//    private fun initializeComponents() {
//        binding.apply {
//            pinNumberBinding.apply {
//                btnNum1.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum2.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum3.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum4.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum5.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum6.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum7.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum8.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum9.setOnClickListener(this@TransferPinFromQRFragment)
//                btnNum0.setOnClickListener(this@TransferPinFromQRFragment)
//                btnDelete.setOnClickListener(this@TransferPinFromQRFragment)
//            }
//        }
//    }
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            pinNumberBinding.btnNum1.id -> addNumberToList("1")
//            pinNumberBinding.btnNum2.id -> addNumberToList("2")
//            pinNumberBinding.btnNum3.id -> addNumberToList("3")
//            pinNumberBinding.btnNum4.id -> addNumberToList("4")
//            pinNumberBinding.btnNum5.id -> addNumberToList("5")
//            pinNumberBinding.btnNum6.id -> addNumberToList("6")
//            pinNumberBinding.btnNum7.id -> addNumberToList("7")
//            pinNumberBinding.btnNum8.id -> addNumberToList("8")
//            pinNumberBinding.btnNum9.id -> addNumberToList("9")
//            pinNumberBinding.btnNum0.id -> addNumberToList("0")
//            pinNumberBinding.btnDelete.id -> {
//                if (numberList.isNotEmpty()) {
//                    numberList.removeAt(numberList.size - 1)
//                    passNumber(numberList)
//                }
//            }
//        }
//    }
//
//    private fun addNumberToList(number: String) {
//        if (numberList.size < 6) {
//            numberList.add(number)
//            passNumber(numberList)
//        }
//    }
//
//    private fun passNumber(numberList: ArrayList<String>) {
//        clearPinDisplay()
//
//        for (i in numberList.indices) {
//            input[i] = numberList[i]
//            when (i) {
//                0 -> pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                1 -> pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                2 -> pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                3 -> pinInputBinding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                4 -> pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                5 -> {
//                    pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
//                    val passCode = input.joinToString("")
//                    if (passCode.length == 6) {
//                        verifyPinAndTransfer(passCode)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun verifyPinAndTransfer(passCode: String) {
//        val accountDestinationNo = sharedPreferences.getString("accountDestinationNoFromQR", "") ?: ""
//        val transferAmount = sharedPreferences.getInt("transferAmountFromQR", 0)
//        val transferDescription = sharedPreferences.getString("transferDescriptionFromQr", "") ?: ""
//        val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
//        val dateTimePlus7Hours = currentDateTime.plusHours(14)
//        val pin = sharedPreferencesRegister.getString("pin", "") ?: ""
//
//        lifecycleScope.launch {
//            viewModel.initializeData()
//            if (passCode == pin) {
//                viewModel.transfer(
//                    pin = pin,
//                    accountTo = accountDestinationNo,
//                    amount = transferAmount,
//                    description = transferDescription,
//                    datetime = dateTimePlus7Hours.toString(),
//                    destinationBank = "Lumi Bank"
//                )
//            } else {
//                makeToast(requireContext(), "PIN salah")
//                clearPinDisplay()
//                numberList.clear()
//                input = Array(6) { "" }
//            }
//        }
//    }
//
//    private fun clearPinDisplay() {
//        pinInputBinding.apply {
//            tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//            tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//            tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//            tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//            tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//            tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
////
////class TransferPinFromQRFragment : Fragment(), View.OnClickListener {
////
////    private var _binding: FragmentTransferPinFromQRBinding? = null
////    private val binding get() = _binding!!
////    private val viewModel by viewModel<TransferViewModel>()
////    private lateinit var pinNumberBinding: PinNumberBinding
////    private lateinit var pinInputBinding: PinInputBinding
////    private lateinit var sharedPreferences: SharedPreferences
////    private lateinit var sharedPreferencesRegister: SharedPreferences
////
////    private val numberList = ArrayList<String>()
////    private var input1: String? = null
////    private var input2: String? = null
////    private var input3: String? = null
////    private var input4: String? = null
////    private var input5: String? = null
////    private var input6: String? = null
////
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        return FragmentTransferPinFromQRBinding.inflate(layoutInflater).also {
////            _binding = it
////        }.root
////    }
////
////
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////
////        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)
////        sharedPreferencesRegister = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
////
////        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
////        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)
////
////        initializeComponents()
////
////        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
////            val id = result.data?.id
////            sharedPreferences.edit().apply {
////                putString("lastidtransactionFromQr", id)
////                apply()
////            }
////            findNavController().navigate(R.id.action_transferPinFromQRFragment_to_transferDetailFromQrFragment)
////        }
////
////        viewModel.error.observe(viewLifecycleOwner) { error ->
////            makeToast(requireContext(), error.message)
////        }
////
////
////
////    }
////
////    private fun initializeComponents() {
////        binding.apply {
////            pinNumberBinding.btnNum1.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum2.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum3.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum4.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum5.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum6.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum7.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum8.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum9.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnNum0.setOnClickListener(this@TransferPinFromQRFragment)
////            pinNumberBinding.btnDelete.setOnClickListener(this@TransferPinFromQRFragment)
////        }
////    }
////
////    override fun onClick(v: View) {
////        when (v.id) {
////            pinNumberBinding.btnNum1.id -> addNumberToList("1")
////            pinNumberBinding.btnNum2.id -> addNumberToList("2")
////            pinNumberBinding.btnNum3.id -> addNumberToList("3")
////            pinNumberBinding.btnNum4.id -> addNumberToList("4")
////            pinNumberBinding.btnNum5.id -> addNumberToList("5")
////            pinNumberBinding.btnNum6.id -> addNumberToList("6")
////            pinNumberBinding.btnNum7.id -> addNumberToList("7")
////            pinNumberBinding.btnNum8.id -> addNumberToList("8")
////            pinNumberBinding.btnNum9.id -> addNumberToList("9")
////            pinNumberBinding.btnNum0.id -> addNumberToList("0")
////            pinNumberBinding.btnDelete.id -> {
////                if (numberList.isNotEmpty()) {
////                    numberList.removeAt(numberList.size - 1)
////                    passNumber(numberList)
////                }
////            }
////        }
////    }
////
////    private fun addNumberToList(number: String) {
////        if (numberList.size < 6) {
////            numberList.add(number)
////            passNumber(numberList)
////        }
////    }
////
////    private fun passNumber(numberList: ArrayList<String>) {
////        clearPinDisplay()
////
////        for (i in numberList.indices) {
////            when (i) {
////                0 -> {
////                    input1 = numberList[0]
////                    pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                }
////
////                1 -> {
////                    input2 = numberList[1]
////                    pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                }
////
////                2 -> {
////                    input3 = numberList[2]
////                    pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                }
////
////                3 -> {
////                    input4 = numberList[3]
////                    pinInputBinding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                }
////
////                4 -> {
////                    input5 = numberList[4]
////                    pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                }
////
////                5 -> {
////                    input6 = numberList[5]
////                    pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
////                    val passCode = input1 + input2 + input3 + input4 + input5 + input6
////                    if (passCode.length == 6) {
////                        val accountDestinationNo = sharedPreferences.getString("accountDestinationNoFromQR", "") ?: ""
////                        val transferAmount = sharedPreferences.getInt("transferAmountFromQR", 0)
////                        val transferDescription = sharedPreferences.getString("transferDescriptionFromQr", "") ?: ""
////                        val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
////                        val dateTimePlus7Hours = currentDateTime.plusHours(14)
////                        val pin = sharedPreferencesRegister.getString("pin", "") ?: ""
////                        lifecycleScope.launch {
////                            awaitAll(viewModel.initializeData())
////                            if ( passCode == pin){
////                                viewModel.transfer(
////                                    pin = pin,
////                                    accountTo = accountDestinationNo,
////                                    amount = transferAmount,
////                                    description = transferDescription,
////                                    datetime = dateTimePlus7Hours.toString(),
////                                    destinationBank = "Lumi Bank"
////                                )
////                            } else {
////                                makeToast(requireContext(), "PIN salah")
////                                clearPinDisplay()
////                                numberList.clear()
////                            }
////                        }
////                    }
////                }
////            }
////        }
////    }
////
////    private fun clearPinDisplay() {
////        pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////        pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////        pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////        pinInputBinding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////        pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////        pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
////    }
////
////    override fun onDestroy() {
////        super.onDestroy()
////
////        _binding = null
////    }
////
////
////}