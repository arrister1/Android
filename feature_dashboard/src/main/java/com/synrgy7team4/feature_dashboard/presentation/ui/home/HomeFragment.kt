package com.synrgy7team4.feature_dashboard.presentation.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var  sharedPreferences: SharedPrefHelper

    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String
    private lateinit var fullAccNum: String
    private lateinit var hiddenAccNum: String
    private var isBalanceHidden: Boolean = false
    private var isAccNumHidden: Boolean = false




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreferences = SharedPrefHelper((requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Keluar dari aplikasi
                requireActivity().finishAffinity()
            }
        })

        //sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getJwtToken()

        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")

        fullAccNum = getString(R.string.dummy_acc_number)
        hiddenAccNum = formatAccountNumber(fullAccNum)

        if(token != null){
        homeViewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
            binding.tvAccName.text = name
//            binding.tvName.visibility = View.VISIBLE
//            binding.tvAccName.visibility = View.VISIBLE

        }

        homeViewModel.accountNumber.observe(viewLifecycleOwner) { accountNumber ->
           // binding.tvAccNumber.text = accountNumber
           // binding.tvAccNumber.visibility = View.VISIBLE

            fullAccNum = accountNumber
            hiddenAccNum = formatAccountNumber(fullAccNum)
            binding.tvAccNumber.text = if(isAccNumHidden) hiddenAccNum else fullAccNum

        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        homeViewModel.fetchUserData(token)
        } else{
            Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()

        


        binding.tvAccBalance.text = fullBalance
        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)

        binding.toggleBalance.setOnClickListener {
            balanceVisibility()
        }
        binding.toggleAcc.setOnClickListener {
            accNumVisibility()
        }

        binding.btnTransfer.setOnClickListener {
            val transferNav = Uri.parse("app://com.example.app/trans/transferList")
           requireView().findNavController().navigate(transferNav)

        }
    }

    private fun accNumVisibility() {
        if(isAccNumHidden){
            binding.tvAccNumber.text = fullAccNum
            binding.toggleAcc.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
            binding.toggleAcc.contentDescription = "Tampilkan  Nomor Rekening"
        } else {
            binding.tvAccNumber.text = hiddenAccNum
            binding.toggleAcc.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
            binding.toggleAcc.contentDescription = "Sembunyikan Nomor Rekening"

        }
        isAccNumHidden = !isAccNumHidden
    }

    private fun formatAccountNumber(accNum: String): String {
        val visibleDigits = 3
        val length = accNum.length
        return if (length > visibleDigits) {
            val hiddenNum = "*".repeat(length-visibleDigits)
            val visibleNum = accNum.takeLast(visibleDigits)
            "$hiddenNum$visibleNum"
        } else {
            accNum
        }

    }

    private fun balanceVisibility() {
        if (isBalanceHidden) {
            binding.tvAccBalance.text = fullBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
            binding.toggleBalance.contentDescription = "Tampilkan Saldo"

        } else {
            binding.tvAccBalance.text = hiddenBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
            binding.toggleBalance.contentDescription = "Sembunyikan Saldo"

        }
        isBalanceHidden = !isBalanceHidden
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}