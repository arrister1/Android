package com.synrgy7team4.feature_auth.ui.registerScreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentInputPhoneNumberBinding
import com.synrgy7team4.feature_auth.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputPhoneNumberFragment : Fragment() {
    private var _binding: FragmentInputPhoneNumberBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentInputPhoneNumberBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            val hp = binding.tiedtPhoneNumber.text.toString()
            val email = sharedPreferences.getString("email", "user@example.com")
            when {
                hp.isEmpty() -> binding.tiedtPhoneNumber.error = "No Hp Tidak Boleh Kosong"
                hp[0] != '8' -> binding.tiedtPhoneNumber.error =
                    "Nomor HP harus diawali dengan angka 8"

                hp.length < 11 -> binding.tiedtPhoneNumber.error =
                    "Nomor HP harus berjumlah 11-13 digit"

                hp.length > 13 -> binding.tiedtPhoneNumber.error =
                    "Nomor HP harus berjumlah 11-13 digit"

                else -> {
                    viewModel.checkPhoneNumberAvailability(hp)
                    email?.let{
                        viewModel.sendOtp(email, hp)
                    }
                }
            }
        }

        binding.tiedtPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNumber = s.toString()
                when {
                    phoneNumber.isEmpty() -> binding.tiedtPhoneNumber.error =
                        "No Hp Tidak Boleh Kosong"

                    phoneNumber[0] != '8' -> binding.tiedtPhoneNumber.error =
                        "Nomor HP harus diawali dengan angka 8"

                    phoneNumber.length < 11 -> binding.tiedtPhoneNumber.error =
                        "Nomor HP harus berjumlah 11-13 digit"

                    phoneNumber.length > 13 -> binding.tiedtPhoneNumber.error =
                        "Nomor HP harus berjumlah 11-13 digit"
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.isPhoneNumberAvailable.observe(viewLifecycleOwner) { isPhoneNumberAvailable ->
            if (isPhoneNumberAvailable) {
                val hp = binding.tiedtPhoneNumber.text.toString()
                sharedPreferences.edit().putString("hp", hp).apply()
//                makeToast(requireContext(), "Nomor Hp Kamu Berhasil Ditambahkan")
                viewModel.sendOtpResult.observe(viewLifecycleOwner) { sendOtpResult ->
                    if (sendOtpResult.success) {
                        makeToast(requireContext(), sendOtpResult.message)
                        view.findNavController().navigate(R.id.action_inputPhoneNumberFragment_to_otpVerification)
                    }
                }
            }
        }



        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }

        if (isTalkbackEnabled()) {
            binding.tiedtPhoneNumber.accessibilityDelegate = object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfo
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
                }
            }
        }
    }

    private fun isTalkbackEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = am.isEnabled
        val isTouchExplorationEnabled = am.isTouchExplorationEnabled

        return isAccessibilityEnabled && isTouchExplorationEnabled
    }

    private fun setupAccessibility() {
        binding.apply {
            btnBack.contentDescription = getString(R.string.kembali)
            tvPhoneNumber.contentDescription = getString(R.string.no_hp)
            tiedtPhoneNumber.contentDescription = getString(R.string.input_nomor_hp)
            btnNext.contentDescription = getString(R.string.lanjut)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}