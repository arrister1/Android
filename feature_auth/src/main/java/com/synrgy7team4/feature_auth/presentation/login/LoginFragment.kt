package com.synrgy7team4.feature_auth.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentLoginBinding
<<<<<<< HEAD
import com.synrgy7team4.feature_auth.databinding.FragmentOnBoardingBinding
import com.synrgy7team4.feature_auth.databinding.FragmentRegistrationSuccessBinding
=======
>>>>>>> 55e8763235f3309e8668b723b962f7f68c00a1b7


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
<<<<<<< HEAD
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
=======
        //return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
>>>>>>> 55e8763235f3309e8668b723b962f7f68c00a1b7

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener{
            view.findNavController().popBackStack()
        }

        binding.btnMasuk.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }
}