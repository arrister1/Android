package com.synrgy7team4.feature_dashboard.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.feature_dashboard.databinding.FragmentAccountBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val navHandler: NavigationHandler by inject()
    private val viewModel by viewModel<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAccountBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.linearlayKeluarAkun.setOnClickListener {
            viewModel.deleteJwtToken()
        }

        viewModel.isDeleteTokenSuccessful.observe(viewLifecycleOwner) {
            navHandler.navigateToOnBoarding()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}