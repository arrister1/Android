package com.synrgy7team4.feature_auth.presentation.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.synrgy7team4.common.ViewModelFactoryProvider
import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject


class LoginFragment : Fragment() {

    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val sharedPrefHelper: SharedPrefHelper by inject()
    private val viewModel by viewModels<LoginViewModel> {

        val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()
    }

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

        setupAccessibility()

        //val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)


        binding.btnBack.setOnClickListener{
            view.findNavController().popBackStack()
        }

        viewModel.token.observe(viewLifecycleOwner) { token ->
            sharedPrefHelper.saveJwtToken(token)
            //sharedPreferences.edit().putString("token", token).apply()
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isSuccessful.observe(viewLifecycleOwner) {
            setToast("User Login Successfully")
            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

//        viewModel.isSuccessful.observe(viewLifecycleOwner) {
//            setToast("User Login Successfully")
//            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }

        viewModel.error.observe(viewLifecycleOwner) { notError ->
            if (!notError) {
                setToast("Email atau Password anda salah")
            }

            if(isTalkbackEnabled()){
                binding.edtEmail.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
                    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(host, info)
                        info?.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
                    }
                })
            }

            binding.textInputLayout3.setEndIconContentDescription(R.string.hide_password)
            binding.textInputLayout3.setEndIconOnClickListener {
                val isPasswordVisible = binding.edtPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                val contentDescription = if(isPasswordVisible){
                    getString(R.string.hide_password)
                } else {
                    getString(R.string.show_password)
                }
                binding.textInputLayout3.setEndIconContentDescription(contentDescription)

            }
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
                    if  (password.contains(Regex("[^a-zA-Z0-9]"))) {
                        Snackbar.make(view, "Password tidak boleh mengandung simbol", Snackbar.LENGTH_SHORT).show()
                    } else if (password.length < 8) {
                        Snackbar.make(view, "Password harus terdiri dari 8-15 karakter", Snackbar.LENGTH_SHORT).show()
                    } else if (password.length > 15) {
                        Snackbar.make(view, "Password harus terdiri dari 8-15 karakter", Snackbar.LENGTH_SHORT).show()
                    } else {
                        viewModel.loginUser(
                            email,
                            password
                        )
                    }
                }
            }

        }


        binding.edtPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pw = s.toString()

                if (pw.contains(Regex("[^a-zA-Z0-9]"))) {
                    Snackbar.make(view, "Password tidak boleh mengandung simbol", Snackbar.LENGTH_SHORT).show()
                } else if (pw.length < 8) {
                    Snackbar.make(view, "Password harus terdiri dari 8-15 karakter", Snackbar.LENGTH_SHORT).show()
                } else if (pw.length > 15) {
                    Snackbar.make(view, "Password harus terdiri dari 8-15 karakter", Snackbar.LENGTH_SHORT).show()
                } else {
                    binding.edtPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })





    }

    private fun isTalkbackEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
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

    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

        
    

}