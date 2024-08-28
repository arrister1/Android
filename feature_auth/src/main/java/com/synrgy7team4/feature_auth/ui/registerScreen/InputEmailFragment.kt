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
    private var isForgotPasswordMode: Boolean = false

    companion object {
        fun newInstance(isForgotPasswordMode: Boolean): InputEmailFragment {
            return InputEmailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("isForgotPasswordMode", isForgotPasswordMode)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isForgotPasswordMode = it.getBoolean("isForgotPasswordMode", false)
        }
    }

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
        setupUI()
        setupListeners()
        observeViewModel()

        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
    }

    private fun setupUI() {
        if (isForgotPasswordMode) {
            binding.tvEmail.text = getString(R.string.email)
            binding.btnNext.text = getString(R.string.kirim_otp)
        } else {
            binding.tvEmail.text = getString(R.string.email)
            binding.btnNext.text = getString(R.string.next)
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            val email = binding.tiedtEmail.text.toString()
            when {
                email.isEmpty() -> binding.tiedtEmail.error = "Email Tidak Boleh Kosong!"
                !isValidEmail(email) -> binding.tiedtEmail.error = "Format Email Tidak Valid"
                else -> {
                    if (isForgotPasswordMode) {
                        viewModel.sendForgetPass(email)
                    } else {
                        viewModel.checkEmailAvailability(email)
                    }
                }
            }
        }

        binding.tiedtEmail.addTextChangedListener(createEmailTextWatcher())
    }

    private fun observeViewModel() {
        viewModel.sendForgetPassResult.observe(viewLifecycleOwner) { result ->
            saveEmailToPreferences(binding.tiedtEmail.text.toString())
            sharedPreferences.edit().putBoolean("isForgotPassword", true).apply()
            findNavController().navigate(R.id.action_inputEmailFragment_to_otpVerification)
            if (result.status) {
                makeToast(requireContext(), "Email tidak terdaftar")
            } else {
                makeToast(requireContext(), "Kode OTP telah dikirim ke email anda")

            }
        }

        viewModel.isEmailAvailable.observe(viewLifecycleOwner) { isEmailAvailable ->
            if (isEmailAvailable) {
                saveEmailToPreferences(binding.tiedtEmail.text.toString())
                makeToast(requireContext(), "Email dapat digunakan")
                findNavController().navigate(R.id.action_inputEmailFragment_to_inputPhoneNumberFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun createEmailTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isValidEmail(s.toString())) {
                    binding.tiedtEmail.error = "Format Email Tidak Valid"
                } else {
                    binding.tiedtEmail.error = null
                }
            }
        }
    }

    private fun saveEmailToPreferences(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }

    private fun setupAccessibility() {
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

        binding.apply {
            btnBack.contentDescription = getString(R.string.kembali)
            tvEmail.contentDescription = if (isForgotPasswordMode)
                getString(R.string.email) else getString(R.string.email)
            tiedtEmail.contentDescription = getString(R.string.input_email)
            btnNext.contentDescription = if (isForgotPasswordMode)
                getString(R.string.lanjut) else getString(R.string.lanjut)
        }
    }

    private fun isTalkbackEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.isEnabled && am.isTouchExplorationEnabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//class InputEmailFragment : Fragment() {
//    private var _binding: FragmentInputEmailBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel by viewModel<RegisterViewModel>()
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return FragmentInputEmailBinding.inflate(layoutInflater).also {
//            _binding = it
//        }.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupAccessibility()
//
//        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//
//        sharedPreferences =
//            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
//
//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        val isForgotPassword = arguments?.getBoolean("isForgotPassword", false) ?: false
//
//        binding.btnNext.setOnClickListener {
//            val email = binding.tiedtEmail.text.toString()
//            when {
//                email.isEmpty() -> binding.tiedtEmail.error = "Email Tidak Boleh Kosong!"
//                !email.matches(emailPattern.toRegex()) -> binding.tiedtEmail.error = "Format Email Tidak Valid"
//
//                else -> {
//                    if(isForgotPassword) {
//                        viewModel.sendForgetPass(email)
//                    } else {
//                        viewModel.checkEmailAvailability(email)
//                    }
//                }
//            }
//        }
//
//        binding.tiedtEmail.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                if (!s.toString().matches(emailPattern.toRegex())) {
//                    binding.tiedtEmail.error = "Format Email Tidak Valid"
//                } else {
//                    binding.tiedtEmail.error = null
//                }
//            }
//        })
//
//        viewModel.sendForgetPassResult.observe(viewLifecycleOwner){ result ->
//            if(result.status) {
//                sharedPreferences.edit().putString("email", binding.tiedtEmail.toString()).apply()
//                sharedPreferences.edit().putBoolean("isForgotPassword", true).apply()
//                findNavController().navigate(R.id.action_inputEmailFragment_to_otpVerification)
//            } else {
//                makeToast(requireContext(), "Email tidak terdaftar")
//            }
//
//        }
//
//        viewModel.isEmailAvailable.observe(viewLifecycleOwner) { isEmailAvailable ->
//            if (isEmailAvailable) {
//                val email = binding.tiedtEmail.text.toString()
//                sharedPreferences.edit().putString("email", email).apply()
//
//                makeToast(requireContext(), "Email dapat digunakan")
//                requireView().findNavController().navigate(R.id.action_inputEmailFragment_to_inputPhoneNumberFragment)
//            }
//        }
//
//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            makeToast(requireContext(), error.message)
//        }
//
//
//        if (isTalkbackEnabled()) {
//            binding.tiedtEmail.accessibilityDelegate = object : View.AccessibilityDelegate() {
//                override fun onInitializeAccessibilityNodeInfo(
//                    host: View,
//                    info: AccessibilityNodeInfo
//                ) {
//                    super.onInitializeAccessibilityNodeInfo(host, info)
//                    info.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
//                }
//            }
//        }
//    }
//
//    private fun isTalkbackEnabled(): Boolean {
//        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
//        val isAccessibilityEnabled = am.isEnabled
//        val isTouchExplorationEnabled = am.isTouchExplorationEnabled
//
//        return isAccessibilityEnabled && isTouchExplorationEnabled
//    }
//
//    private fun setupAccessibility() {
//        binding.apply {
//            btnBack.contentDescription = getString(R.string.kembali)
//            tvEmail.contentDescription = getString(R.string.email)
//            tiedtEmail.contentDescription = getString(R.string.input_email)
//            btnNext.contentDescription = getString(R.string.lanjut)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}