package com.synrgy7team4.feature_dashboard.presentation.ui.account

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_dashboard.databinding.FragmentAccountBinding
import org.koin.android.ext.android.inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAccountBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AccountViewModel::class.java]

        binding.linearlayKeluarAkun.setOnClickListener {
            sharedPrefHelper.deleteJwtToken()

            val deepLinkUri = Uri.parse("app://com.example.app/auth/login")
            view.findNavController().navigate(deepLinkUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}