package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.synrgy7team4.common.databinding.PinInputBinding
import com.synrgy7team4.common.databinding.PinNumberBinding
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentPinConfirmationBinding

class PinConfirmationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPinConfirmationBinding? = null
    private val binding get() = _binding!!


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
    private lateinit var firstPassCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinNumberBinding = PinNumberBinding.bind(binding.pinNumber.root)
        pinInputBinding = PinInputBinding.bind(binding.pinInput.root)
        // Mengambil passCode dari Bundle
        firstPassCode = arguments?.getString("passCode") ?: ""
        initializeComponents()
    }

    private fun initializeComponents() {
        binding.apply {
            pinNumberBinding.btnNum1.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum2.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum3.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding. btnNum4.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum5.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum6.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum7.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnNum8.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding. btnNum9.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding. btnNum0.setOnClickListener(this@PinConfirmationFragment)
            pinNumberBinding.btnDelete.setOnClickListener(this@PinConfirmationFragment)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            pinNumberBinding.btnNum1.id-> addNumberToList("1")
            pinNumberBinding.btnNum2.id -> addNumberToList("2")
            pinNumberBinding.btnNum3.id -> addNumberToList("3")
            pinNumberBinding.btnNum4.id -> addNumberToList("4")
            pinNumberBinding.btnNum5.id -> addNumberToList("5")
            pinNumberBinding.btnNum6.id -> addNumberToList("6")
            pinNumberBinding.btnNum7.id -> addNumberToList("7")
            pinNumberBinding.btnNum8.id -> addNumberToList("8")
            pinNumberBinding.btnNum9.id -> addNumberToList("9")
            pinNumberBinding.btnDelete.id -> {
                if (numberList.isNotEmpty()) {
                    numberList.removeAt(numberList.size - 1)
                    passNumber(numberList)
                }
            }
        }
    }

    private fun addNumberToList(number: String) {
        numberList.add(number)
        passNumber(numberList)
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
                        matchPassCode()
                    }
                }
            }
        }
    }

    private fun clearPinDisplay() {
        binding.apply {
            pinInputBinding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            pinInputBinding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            pinInputBinding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            pinInputBinding. tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            pinInputBinding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            pinInputBinding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
        }
    }

    private fun matchPassCode() {
        if (firstPassCode == passCode) {
            requireView().findNavController().navigate(R.id.action_pinConfirmationFragment_to_registrationSuccessFragment)
        } else {
            Toast.makeText(requireContext(), "Password doesn't match", Toast.LENGTH_SHORT).show()
            numberList.clear()
            clearPinDisplay()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
