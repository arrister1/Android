package com.synrgy7team4.feature_auth.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding
import com.synrgy7team4.feature_dashboard.presentation.DashboardActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewmodel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.btnMasuk.setOnClickListener {
            viewmodel.login(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        viewmodel.isSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                val intent = Intent(activity, DashboardActivity::class.java)
                activity?.startActivity(intent)
//                view.findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }
        }

        viewmodel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        }

        viewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Show loading
            } else {
                // Hide loading
            }
        }
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