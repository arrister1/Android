package com.synrgy7team4.feature_auth.ui.registerScreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.synrgy7team4.common.makeSnackbar
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentCreatePasswordBinding

class CreatePasswordFragment : Fragment() {
    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCreatePasswordBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.submitCreatedPassword.setOnClickListener {
            val password = binding.inputPassword.text.toString()
            val passwordConfirmation = binding.inputPasswordConfirmation.text.toString()

            when {
                password.isEmpty() -> binding.inputPassword.error = "Password tidak boleh kosong"
                passwordConfirmation.isEmpty() -> binding.inputPasswordConfirmation.error =
                    "Password tidak boleh kosong"

                else -> {
                    if (password.contains(Regex("[^a-zA-Z0-9]"))) {
                        Snackbar.make(
                            view,
                            "Password tidak boleh mengandung simbol",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else if (password.length < 8) {
                        Snackbar.make(
                            view,
                            "Password harus terdiri dari 8-15 karakter",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else if (password.length > 15) {
                        Snackbar.make(
                            view,
                            "Password harus terdiri dari 8-15 karakter",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        if (passwordConfirmation != password) {
                            Snackbar.make(
                                view,
                                "Password tidak sama, mohon input kembali",
                                Snackbar.LENGTH_SHORT
                            ).show()

                        } else {
                            sharedPreferences.edit().putString("password", password).apply()
                            makeToast(requireContext(), "Kamu berhasil membuat password")
                            view.findNavController()
                                .navigate(R.id.action_createPasswordFragment_to_biodataFragment)
                        }
                    }
                }
            }

            binding.inputPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val pw = s.toString()
                    if (pw.contains(Regex("[^a-zA-Z0-9]"))) {
                        makeSnackbar(view, "Password tidak boleh mengandung simbol")
                    } else if (pw.length < 8) {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    } else if (pw.length > 15) {
                        makeSnackbar(view, "Password harus terdiri dari 8-15 karakter")
                    } else {
                        binding.inputPassword.error = null
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}