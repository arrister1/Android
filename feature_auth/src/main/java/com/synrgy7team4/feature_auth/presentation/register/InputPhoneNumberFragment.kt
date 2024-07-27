package com.synrgy7team4.feature_auth.presentation.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jer.shared.ViewModelFactoryProvider
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentInputPhoneNumberBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel

class InputPhoneNumberFragment : Fragment() {
    private val binding by lazy { FragmentInputPhoneNumberBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel by viewModels<RegisterViewModel> {
//        val app = requireActivity().application
//        (app as MyApplication).viewModelFactory

        val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()

        }


        binding.btnNext.setOnClickListener {
            val hp = binding.tiedtPhoneNumber.text.toString()
            when {
                hp.isEmpty() -> binding.tiedtPhoneNumber.error = "No Hp Tidak Boleh Kodong"
                else -> {
                    sharedPreferences.edit().putString("hp", hp).apply()
                    setToast("Nomor $hp Kamu Berhasil Ditambahkan")

                    view.findNavController().navigate(R.id.action_inputPhoneNumberFragment_to_otpVerification)
                }
            }

        }
        
    }

    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()

    }
}