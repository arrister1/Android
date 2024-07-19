package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentPinConfirmationBinding

class PinConfirmationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPinConfirmationBinding? = null
    private val binding get() = _binding!!

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
        // Mengambil passCode dari Bundle
        firstPassCode = arguments?.getString("passCode") ?: ""
        initializeComponents()
    }

    private fun initializeComponents() {
        binding.apply {
            btnNum1.setOnClickListener(this@PinConfirmationFragment)
            btnNum2.setOnClickListener(this@PinConfirmationFragment)
            btnNum3.setOnClickListener(this@PinConfirmationFragment)
            btnNum4.setOnClickListener(this@PinConfirmationFragment)
            btnNum5.setOnClickListener(this@PinConfirmationFragment)
            btnNum6.setOnClickListener(this@PinConfirmationFragment)
            btnNum7.setOnClickListener(this@PinConfirmationFragment)
            btnNum8.setOnClickListener(this@PinConfirmationFragment)
            btnNum9.setOnClickListener(this@PinConfirmationFragment)
            btnNum0.setOnClickListener(this@PinConfirmationFragment)
            btnDelete.setOnClickListener(this@PinConfirmationFragment)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_num_1 -> addNumberToList("1")
            R.id.btn_num_2 -> addNumberToList("2")
            R.id.btn_num_3 -> addNumberToList("3")
            R.id.btn_num_4 -> addNumberToList("4")
            R.id.btn_num_5 -> addNumberToList("5")
            R.id.btn_num_6 -> addNumberToList("6")
            R.id.btn_num_7 -> addNumberToList("7")
            R.id.btn_num_8 -> addNumberToList("8")
            R.id.btn_num_9 -> addNumberToList("9")
            R.id.btn_delete -> {
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
                    binding.tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                1 -> {
                    input2 = numberList[1]
                    binding.tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                2 -> {
                    input3 = numberList[2]
                    binding.tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                3 -> {
                    input4 = numberList[3]
                    binding.tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                4 -> {
                    input5 = numberList[4]
                    binding.tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
                }
                5 -> {
                    input6 = numberList[5]
                    binding.tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet_filled)
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
            tvPinInput1.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            tvPinInput2.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            tvPinInput3.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            tvPinInput4.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            tvPinInput5.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
            tvPinInput6.setBackgroundResource(com.synrgy7team4.common.R.drawable.pin_bullet)
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
