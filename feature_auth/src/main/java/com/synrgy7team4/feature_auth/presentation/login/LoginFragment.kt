package com.synrgy7team4.feature_auth.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        return inflater.inflate(R.layout.fragment_login, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnBack.setOnClickListener{
            view.findNavController().popBackStack()
        }

        binding.btnMasuk.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        setupAccessibility()

    }

    private fun setupAccessibility() {
        binding.apply {
            textViewMasuk.contentDescription = getString(R.string.masuk)
            btnBack.contentDescription = getString(R.string.tombol_kembali)
            textViewEmail.contentDescription = getString(R.string.email)
            edtEmail.contentDescription = getString(R.string.input_email)
            textViewPw.contentDescription = getString(R.string.password)
            edtPassword.contentDescription = getString(R.string.input_password)
            btnMasuk.contentDescription = getString(R.string.tombol_masuk)
        }
    }



        
    

}