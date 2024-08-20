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
import com.synrgy7team4.feature_auth.databinding.FragmentInputEmailBinding
import com.synrgy7team4.feature_auth.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputEmailFragment : Fragment() {
    private var _binding: FragmentInputEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentInputEmailBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            val email = binding.tiedtEmail.text.toString()
            when {
                email.isEmpty() -> binding.tiedtEmail.error = "Email Tidak Boleh Kosong!"
                !email.matches(emailPattern.toRegex()) -> binding.tiedtEmail.error = "Format Email Tidak Valid"

                else -> {
                    viewModel.checkEmailAvailability(email)
                }
            }
        }

        binding.tiedtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().matches(emailPattern.toRegex())) {
                    binding.tiedtEmail.error = "Format Email Tidak Valid"
                } else {
                    binding.tiedtEmail.error = null
                }
            }
        })

        viewModel.isEmailAvailable.observe(viewLifecycleOwner) { isEmailAvailable ->
            if (isEmailAvailable) {
                val email = binding.tiedtEmail.text.toString()
                sharedPreferences.edit().putString("email", email).apply()
                makeToast(requireContext(), "Alamat Email Berhasil Terdaftar")
                requireView().findNavController().navigate(R.id.action_inputEmailFragment_to_inputPhoneNumberFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }

        if (isTalkbackEnabled()) {
            binding.tiedtEmail.accessibilityDelegate = object : View.AccessibilityDelegate() {
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
            tvEmail.contentDescription = getString(R.string.email)
            tiedtEmail.contentDescription = getString(R.string.input_email)
            btnNext.contentDescription = getString(R.string.lanjut)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}