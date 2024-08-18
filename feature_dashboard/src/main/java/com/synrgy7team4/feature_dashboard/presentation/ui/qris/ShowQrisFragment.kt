package com.synrgy7team4.feature_dashboard.presentation.ui.qris

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentShowQrisBinding
import com.synrgy7team4.feature_dashboard.presentation.ui.home.HomeViewModel
import com.synrgy7team4.feature_dashboard.utility.QrUtility


// Class nya tak digunakan, yang digunakan hanya layoutnya saja

class ShowQrisFragment : Fragment() {


    private val binding by lazy { FragmentShowQrisBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences
    private var isHidden: Boolean = false
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            HomeViewModel::class.java)
       sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")

        val getAccountNumber = sharedPreferences.getString("accountNumber", null)
        val getPayText = sharedPreferences.getString("payOrReceived", null)
        val getToken = sharedPreferences.getString("token", null)

        binding.tvGet.text = getPayText

//        viewModel.fectData(getToken, getAccountNumber)
        viewModel.userResponse.observe(viewLifecycleOwner) { data ->
            binding.amountTextView.text = data?.data.toString()
            binding.accountNo.text = getAccountNumber
        }

        val mQRBitmap = QrUtility.generateQR(getAccountNumber!!)
        if (mQRBitmap != null) {
            binding.qrImageView.setImageBitmap(mQRBitmap)
        } else {
            Toast.makeText(requireActivity(), "Failed to Generated QR Code", Toast.LENGTH_SHORT).show()
        }

        binding.ivToggleBalance.setOnClickListener {
            balanceVisibility()
        }


    }







    private fun balanceVisibility() {
        if (isHidden) {
            binding.ammount.text = fullBalance
            binding.ivToggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
        } else {
            binding.ammount.text = hiddenBalance
            binding.ivToggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
        }
        isHidden = !isHidden
    }


}