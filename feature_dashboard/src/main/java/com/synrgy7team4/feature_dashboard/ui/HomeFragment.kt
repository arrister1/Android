package com.synrgy7team4.feature_dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_dashboard.databinding.FragmentHomeBinding
import com.synrgy7team4.feature_dashboard.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navHandler: NavigationHandler by inject()
    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var hiddenBalance: String
    private var isHidden: Boolean = false
    private var userBalance: String = "0.0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()

        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)

        binding.toggleBalance.setOnClickListener {
            balanceVisibility()
        }

        binding.btnTransfer.setOnClickListener {
            navHandler.navigateToTransfer()
        }

        binding.btnMutasi.setOnClickListener {
            navHandler.navigateToMutasi()
        }

        viewModel.userBalance.observe(viewLifecycleOwner) { balance ->
            userBalance = balance.toString()
            binding.tvAccBalance.text = userBalance
            hiddenBalance = userBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")
        }

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            viewModel.getUserBalance()
            binding.tvName.text = userData.name
            binding.tvAccName.text = userData.name
            binding.tvAccNumber.text = userData.accountNumber
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }
    }

    private fun balanceVisibility() {
        if (isHidden) {
            binding.tvAccBalance.text = userBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
        } else {
            binding.tvAccBalance.text = hiddenBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
        }
        isHidden = !isHidden
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}