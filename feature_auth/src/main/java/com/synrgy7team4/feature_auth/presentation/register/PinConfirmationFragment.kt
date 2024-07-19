package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentPinConfirmationBinding
import com.synrgy7team4.feature_auth.databinding.FragmentVerifikasiKtpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PinConfirmationFragment : Fragment() {
    private var _binding: FragmentPinConfirmationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPinConfirmationBinding.inflate(inflater,container,false);
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delayAndNavigate()
    }

    private fun delayAndNavigate() {
        lifecycleScope.launch {
            delay(5000) // Delay for 10 seconds (10000 milliseconds)
            requireView().findNavController().navigate(R.id.action_pinConfirmationFragment_to_registrationSuccessFragment)
        }
    }


}