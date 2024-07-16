package com.synrgy7team4.bankingapps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreatePassword : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.submitCreatedPassword).setOnClickListener { getPassword(view) }
    }

    private fun getPassword(view:View){
        val password = view.findViewById<TextInputEditText>(R.id.inputPassword).text
        val passwordConfirmation = view.findViewById<TextInputEditText>(R.id.inputPasswordConfirmation).text

        Log.d("password", password.toString())
        Log.d("passwordConfirmation", passwordConfirmation.toString())
    }
}