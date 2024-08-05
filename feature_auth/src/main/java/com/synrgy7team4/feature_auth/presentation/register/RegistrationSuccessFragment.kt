package com.synrgy7team4.feature_auth.presentation.register

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jer.shared.ViewModelFactoryProvider
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentRegistrationSuccessBinding
import com.synrgy7team4.feature_auth.databinding.FragmentVerifikasiKtpBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel

class RegistrationSuccessFragment : Fragment() {
    private var _binding: FragmentRegistrationSuccessBinding? = null
    private val binding get() = _binding!!

    private val DELAY_MILLIS: Long = 2000

    private val viewModel by viewModels<RegisterViewModel> {
//        val app = requireActivity().application
//        (app as MyApplication).viewModelFactory

        val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationSuccessBinding.inflate(inflater,container,false);
        val view = binding.root;
        return view;
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Handler(Looper.getMainLooper()).postDelayed({
           val deepLinkUri = Uri.parse("app://com.example.app/auth/login" )
           findNavController().navigate(deepLinkUri)
           // findNavController().navigate(R.id.action_registrationSuccessFragment_to_homeFragment)
       }, DELAY_MILLIS)

//        binding.regisSuccessLayout.setOnClickListener {
//            if (viewModel.isDataComplete()) {
//                viewModel.registerUser()
//                setToast("Terimakasih telah melengkapi data kamu")
//                requireView().findNavController().navigate(R.id.action_registrationSuccessFragment_to_homeFragment)
//
//
//            } else {
//                setToast("Mohon lengkapi data terlebih dahulu")
//            }

//            requireView().findNavController().navigate(R.id.action_registrationSuccessFragment_to_homeFragment)
//
//        }


    }

    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()

    }

}
