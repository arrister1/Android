package com.synrgy7team4.feature_auth.ui.registerScreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOtpVerificationBinding
import com.synrgy7team4.feature_auth.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpVerification : Fragment() {
    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel by viewModel<RegisterViewModel>()

    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentOtpVerificationBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputCode1 = view.findViewById(R.id.inputCode1)
        inputCode2 = view.findViewById(R.id.inputCode2)
        inputCode3 = view.findViewById(R.id.inputCode3)
        inputCode4 = view.findViewById(R.id.inputCode4)
        inputCode5 = view.findViewById(R.id.inputCode5)
        inputCode6 = view.findViewById(R.id.inputCode6)


        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "user@example.com")
        val hp = sharedPreferences.getString("hp", "88888888111")
        binding.tvNumber.text = email
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.submitOTPButton.setOnClickListener {
            val otp = getOTP()
            email?.let {
                viewModel.verifyOtp(it, otp)
            }

//            view.findNavController().navigate(R.id.action_otpVerification_to_createPasswordFragment)
        }
        viewModel.verifyOtpResult.observe(viewLifecycleOwner) { otpResponse ->
            val otp = getOTP()
            if (otpResponse.success) {
                Log.d("OTP", otp)
                makeToast(requireContext(), otpResponse.message)
                sharedPreferences.edit().putString("otp", otp).apply()
                view.findNavController().navigate(R.id.action_otpVerification_to_createPasswordFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.toString())
        }

        object : CountDownTimer(60000, 1000) {


            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.countDown.text = "00:" + seconds
            }

            override fun onFinish() {
                viewModel.sendOtp(email.toString(), hp.toString())
                binding.countDown.text = "00:00 (otp sudah dikirim ulang)"
            }
        }.start()

        binding.btnResendOtp.setOnClickListener {
            viewModel.sendOtp(email.toString(), hp.toString())
        }

        viewModel.sendOtpResult.observe(viewLifecycleOwner) { otpResponse ->
            if (otpResponse.success) {
                Log.d("OTP", otpResponse.message)
                makeToast(requireContext(), otpResponse.message)
            }
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        setupOTPInputs()
    }

    private fun setupOTPInputs() {
        //when use input number
        addTextWatcher(inputCode1, inputCode2)
        addTextWatcher(inputCode2, inputCode3)
        addTextWatcher(inputCode3, inputCode4)
        addTextWatcher(inputCode4, inputCode5)
        addTextWatcher(inputCode5, inputCode6)

        //when use delete or erase
        inputCode1.setOnKeyListener(GenericKeyEvent(inputCode1, null))
        inputCode2.setOnKeyListener(GenericKeyEvent(inputCode2, inputCode1))
        inputCode3.setOnKeyListener(GenericKeyEvent(inputCode3, inputCode2))
        inputCode4.setOnKeyListener(GenericKeyEvent(inputCode4, inputCode3))
        inputCode5.setOnKeyListener(GenericKeyEvent(inputCode5, inputCode4))
        inputCode6.setOnKeyListener(GenericKeyEvent(inputCode6, inputCode5))
    }

    private fun addTextWatcher(inputCodeFirst: EditText, inputCodeSecond: EditText) {
        return inputCodeFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    inputCodeSecond.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getOTP(): String {
        return "${inputCode1.text}${inputCode2.text}${inputCode3.text}${inputCode4.text}${inputCode5.text}${inputCode6.text}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.inputCode1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}