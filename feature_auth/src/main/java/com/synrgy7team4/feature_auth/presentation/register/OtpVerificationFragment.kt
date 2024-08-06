package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOtpVerificationBinding

class OtpVerificationFragment : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_otp_verification, container, false)
        _binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateCountdown(view);

        inputCode1 = view.findViewById(R.id.inputCode1)
        inputCode2 = view.findViewById(R.id.inputCode2)
        inputCode3 = view.findViewById(R.id.inputCode3)
        inputCode4 = view.findViewById(R.id.inputCode4)
        inputCode5 = view.findViewById(R.id.inputCode5)
        inputCode6 = view.findViewById(R.id.inputCode6)

        setupOTPInputs()

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.submitOTPButton.setOnClickListener {submitOTP()}

        binding.sendOtpCode.setOnClickListener {sendOtpCode(view)}
    }

    private fun sendOtpCode(view:View) {
        val countDownTextView = view.findViewById<TextView>(R.id.sendOtpCode)
        val validString = resources.getString(R.string.kirim_ulang_otp)
        if (countDownTextView.text == validString){
            Toast.makeText(activity,"sending otp code again", Toast.LENGTH_SHORT).show();
            initiateCountdown(view);
        }
    }

    private fun initiateCountdown(view:View) {
        val countDownTextView = view.findViewById<TextView>(R.id.sendOtpCode)

        val timer = object: CountDownTimer(60*1000, 1*1000) {
            override fun onTick(millisUntilFinished: Long) {
                val countDownString = "00:${(millisUntilFinished/1000).toInt()}";
                countDownTextView.text = countDownString;
                countDownTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            }

            override fun onFinish() {
                countDownTextView.text = resources.getString(R.string.kirim_ulang_otp);
                countDownTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_blue));
            }
        }
        timer.start()
    }

    private fun submitOTP() {
        val correctOtp = "123456";
        val currentOtp = getOtpInString();

        if(currentOtp == correctOtp)
        {
            view?.findNavController()?.navigate(R.id.action_otpVerification_to_createPassword)
        }else
        {
            Toast.makeText(activity,"OTP not match!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun getOtpInString(): String {
        return "${inputCode1.text}${inputCode2.text}${inputCode3.text}${inputCode4.text}${inputCode5.text}${inputCode6.text}"
    }

    private fun setupOTPInputs() {
        //when use input number
        addTextWatcher(inputCode1,inputCode2)
        addTextWatcher(inputCode2,inputCode3)
        addTextWatcher(inputCode3,inputCode4)
        addTextWatcher(inputCode4,inputCode5)
        addTextWatcher(inputCode5,inputCode6)

        //when use delete or erase
        inputCode1.setOnKeyListener(GenericKeyEvent(inputCode1, null))
        inputCode2.setOnKeyListener(GenericKeyEvent(inputCode2, inputCode1))
        inputCode3.setOnKeyListener(GenericKeyEvent(inputCode3, inputCode2))
        inputCode4.setOnKeyListener(GenericKeyEvent(inputCode4, inputCode3))
        inputCode5.setOnKeyListener(GenericKeyEvent(inputCode5, inputCode4))
        inputCode6.setOnKeyListener(GenericKeyEvent(inputCode6, inputCode5))
    }

    private fun addTextWatcher(inputCodeFirst:EditText, inputCodeSecond:EditText) {
        return inputCodeFirst.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    inputCodeSecond.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.inputCode1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}