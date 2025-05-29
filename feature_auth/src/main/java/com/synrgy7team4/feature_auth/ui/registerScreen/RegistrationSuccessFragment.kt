package com.synrgy7team4.feature_auth.ui.registerScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentRegistrationSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationSuccessFragment : Fragment() {
    private var _binding: FragmentRegistrationSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegistrationSuccessBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_registrationSuccessFragment_to_loginFragment)
        }
    }

    private fun setupAccessibility() {
        binding.apply {
            tvRegisSuccess.contentDescription = getString(R.string.anda_berhasil_membuka_rekening)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}