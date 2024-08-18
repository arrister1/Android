package com.synrgy7team4.feature_auth.ui.registerScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentVerifikasiKtpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VerifikasiKtpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentVerifikasiKtpBinding.inflate(layoutInflater).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delayAndNavigate()
    }

    private fun delayAndNavigate() {
        lifecycleScope.launch {
            delay(3000)
            requireView().findNavController().navigate(R.id.action_verifikasiKtpFragment_to_fingerprintVerifFragment)
        }
    }
}