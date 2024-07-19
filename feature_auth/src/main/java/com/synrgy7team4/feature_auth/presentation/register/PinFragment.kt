package com.synrgy7team4.feature_auth.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentPinBinding
import com.synrgy7team4.feature_auth.databinding.FragmentVerifikasiKtpBinding

class PinFragment : Fragment() {
    private var _binding: FragmentPinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPinBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.tvPinInput6.
//        requireView().findNavController().navigate(R.id.action_verifikasiKtpFragment_to_fingerprintVerifFragment)
        binding.btnNum1.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_pinFragment_to_pinConfirmationFragment)

        }
    }
}