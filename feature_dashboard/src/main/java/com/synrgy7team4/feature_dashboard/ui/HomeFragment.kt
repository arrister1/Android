package com.synrgy7team4.feature_dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.formatRupiah
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
    private var isBalanceHidden: Boolean = true
    private var userBalance: String = "0"

    private lateinit var fullAccNum: String
    private lateinit var hiddenAccNum: String
    private var isAccNumHidden: Boolean = true


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
            userBalance = formatRupiah(balance.toString())
            hiddenBalance = userBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")
            updateBalanceVisibility()
        }

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            viewModel.getUserBalance()
            fullAccNum = userData.accountNumber
            hiddenAccNum = formatAccountNumber(fullAccNum)
            binding.tvName.text = userData.name
            binding.tvAccName.text = userData.name
            updateAccNumVisibility()
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

    private fun updateBalanceVisibility(){
        binding.tvAccBalance.text = if (isBalanceHidden) hiddenBalance else userBalance
        binding.toggleBalance.setImageResource(
            if (isBalanceHidden) com.synrgy7team4.common.R.drawable.ic_visibility_on
            else com.synrgy7team4.common.R.drawable.ic_visibility_off
        )
        binding.toggleBalance.contentDescription = if (isBalanceHidden) getString(R.string.tampilkan_saldo) else getString(
            R.string.sembunyikan_saldo
        )
    }

    private fun updateAccNumVisibility() {
        binding.tvAccNumber.text = if (isAccNumHidden) hiddenAccNum else fullAccNum
        binding.toggleAcc.setImageResource(
            if (isAccNumHidden) com.synrgy7team4.common.R.drawable.ic_visibility_on
            else com.synrgy7team4.common.R.drawable.ic_visibility_off
        )
        binding.toggleAcc.contentDescription = if (isAccNumHidden) getString(R.string.tampilkan_nomor_rekening) else "Sembunyikan Nomor Rekening"
    }

    private fun showToast() {
        Toast.makeText(requireContext(),
            getString(R.string.fitur_ini_akan_segera_hadir), Toast.LENGTH_SHORT).show()
    }

    private fun accNumVisibility() {
        isAccNumHidden = !isAccNumHidden
        updateAccNumVisibility()
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
        isBalanceHidden = !isBalanceHidden
        updateBalanceVisibility()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}