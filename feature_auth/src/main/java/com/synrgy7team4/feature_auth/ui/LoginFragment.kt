package com.synrgy7team4.feature_auth.ui

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.makeSnackbar
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding
import com.synrgy7team4.feature_auth.viewmodel.LoginViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val navHandler: NavigationHandler by inject()
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLoginBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("isForgotPassword").apply()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMasuk.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            when {
                email.isEmpty() -> binding.edtEmail.error = "Email tidak boleh kosong"
                password.isEmpty() -> binding.edtPassword.error = "Password tidak boleh kosong"
                else -> {
                    if (!email.matches(emailPattern.toRegex())) {
                        binding.edtEmail.error = "Format email tidak sesuai. Contoh: user@domain.com"
                    }
                    if (password.contains(Regex("[^a-zA-Z0-9]"))) {
                        makeSnackbar(view, "Password tidak boleh mengandung simbol")
                    } else if (password.length < 8) {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    } else if (password.length > 15) {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    } else {
                        viewModel.login(email, password)
                    }
                }
            }
        }

        binding.forgotPassword.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToInputEmailFragment(isForgotPasswordMode = true)
            findNavController().navigate(action)
//            sharedPreferences.edit().putString("isForgotPassword", "true").apply()
//            requireView().findNavController()
//                .navigate(R.id.action_loginFragment_to_inputEmailFragment)
        }

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textInputLayout3.isEndIconVisible = s?.isNotEmpty() == true
                when {
                    s.toString().contains(Regex("[^a-zA-Z0-9]")) -> {
                        makeSnackbar(view, "Password tidak boleh mengandung simbol")
                    }

                    s.toString().length < 8 -> {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    }

                    s.toString().length > 15 -> {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    }

                    else -> binding.edtPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                lifecycleScope.launch {
                    awaitAll(
                        viewModel.saveTokens(),
                        viewModel.saveUserData()
                    )
                    navHandler.navigateToDashboard()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)

            binding.textInputLayout3.setEndIconContentDescription(R.string.hide_password)
            binding.textInputLayout3.setEndIconOnClickListener {
                val isPasswordVisible =
                    binding.edtPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                val contentDescription = if (isPasswordVisible) {
                    binding.edtPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

                    getString(R.string.hide_password)
                } else {
                    binding.edtPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                    getString(R.string.show_password)
                }
                binding.textInputLayout3.endIconContentDescription = contentDescription
                binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)

            }
        }

        if (isTalkbackEnabled()) {
            binding.edtEmail.accessibilityDelegate = object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfo
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info?.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
                }
            }
        }
    }


    private fun isTalkbackEnabled(): Boolean {
        val am =
            requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = am.isEnabled
        val isTouchExplorationEnabled = am.isTouchExplorationEnabled
        return isAccessibilityEnabled && isTouchExplorationEnabled
    }

    private fun setupAccessibility() {
        binding.apply {
            textViewMasuk.contentDescription = getString(R.string.masuk)
            btnBack.contentDescription = getString(R.string.kembali)
            textViewEmail.contentDescription = getString(R.string.email)
            edtEmail.contentDescription = getString(R.string.input_email)
            textViewPw.contentDescription = getString(R.string.password)
            edtPassword.contentDescription = getString(R.string.input_password)
            btnMasuk.contentDescription = getString(R.string.masuk)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}