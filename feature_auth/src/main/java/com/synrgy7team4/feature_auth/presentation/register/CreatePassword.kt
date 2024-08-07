/*
package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentCreatePasswordBinding
import com.synrgy7team4.feature_auth.databinding.FragmentOtpVerificationBinding

class CreatePassword : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_otp_verification, container, false)
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitCreatedPassword.setOnClickListener {
            // Navigate with a delay
            view.postDelayed({
              //  view.findNavController().navigate(R.id.action_createPassword_to_ktpVerificationBoardFragment)
            }, 2000) // Delay 2 seconds
        }

        binding.submitCreatedPassword.setOnClickListener { getPassword() }
    }

    private fun getPassword() {
        val password = binding.inputPassword.text.toString().trim()
        val passwordConfirmation = binding.inputPasswordConfirmation.text.toString().trim()


        Log.d("password", password)
        Log.d("passwordConfirmation", passwordConfirmation)
        if(password == passwordConfirmation)
        {
            requireView().findNavController().navigate(R.id.action_createPassword_to_ktpVerificationBoardFragment2)
        }else{
            Toast.makeText(activity,"password confirmation not match!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/
