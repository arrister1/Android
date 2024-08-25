package com.synrgy7team4.feature_dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_dashboard.R
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
    private var isBalanceHidden: Boolean = false
    private var userBalance: String = "0.0"

    private lateinit var fullAccNum: String
    private lateinit var hiddenAccNum: String
    private var isAccNumHidden: Boolean = false


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

        binding.toggleAcc.setOnClickListener {
            accNumVisibility()
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
            fullAccNum = userData.accountNumber
            hiddenAccNum = formatAccountNumber(fullAccNum)
            binding.tvName.text = userData.name
            binding.tvAccName.text = userData.name
            binding.tvAccNumber.text = if(isAccNumHidden) hiddenAccNum else fullAccNum
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }

        binding.btnEwallet.setOnClickListener {
            showToast()
        }

        binding.btnData.setOnClickListener {
            showToast()
        }

        binding.btnElectric.setOnClickListener {
            showToast()
        }

        binding.btnPulsa.setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(),
            getString(R.string.fitur_ini_akan_segera_hadir), Toast.LENGTH_SHORT).show()
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
        }    }

    private fun balanceVisibility() {
        if (isBalanceHidden) {
            binding.tvAccBalance.text = userBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
        } else {
            binding.tvAccBalance.text = hiddenBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
        }
        isBalanceHidden = !isBalanceHidden
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}