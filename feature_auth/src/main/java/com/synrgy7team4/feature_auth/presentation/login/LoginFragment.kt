package com.synrgy7team4.feature_auth.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding
import com.synrgy7team4.feature_auth.databinding.FragmentRegistrationSuccessBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMasuk.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        binding.btnBack.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

    }

}