package com.synrgy7team4.feature_auth.presentation.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding
import com.synrgy7team4.feature_auth.databinding.FragmentPinBinding
import com.synrgy7team4.feature_auth.databinding.FragmentRegistrationSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPunya.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
        binding.btnBlumPunya.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_onBoardingFragment_to_inputEmailFragment)
        }

    }


}