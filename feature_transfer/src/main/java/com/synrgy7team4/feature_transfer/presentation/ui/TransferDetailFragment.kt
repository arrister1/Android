package com.synrgy7team4.feature_transfer.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessDetailBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessRecipientBinding
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransferDetailFragment : Fragment() {
    private var _binding: FragmentTransferDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var destinationBinding: TransSuccessRecipientBinding
    private lateinit var transactionSuccessBinding: TransSuccessDetailBinding

    private val transferViewModel: TransferViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransferDetailBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", "") ?: ""
        val accountNumber = sharedPreferences.getString("accountNumber", "") ?: ""

        destinationBinding = TransSuccessRecipientBinding.bind(binding.recipient.root)
        transactionSuccessBinding = TransSuccessDetailBinding.bind(binding.transDetail.root)

        transferViewModel.getMutation(token, accountNumber)
        transferViewModel.mutationData.observe(viewLifecycleOwner) { mutationData ->
            // Update the UI with mutationData
            destinationBinding.tvRecipientName.text = mutationData.usernameTo
            destinationBinding.tvBank.text = getString(R.string.lumi_bank) //dummy
            destinationBinding.tvAccNum.text = mutationData.accountTo

            binding.tvNominal.text = mutationData.amount.toString()

            transactionSuccessBinding.transDate.text = mutationData.datetime
        }

        binding.btnDetail.setOnClickListener {
            val deepLinkUri = Uri.parse("app://com.example.app/trans/transSuccess")
            requireView().findNavController().navigate(deepLinkUri)
        }

        binding.btnShare.setOnClickListener {

        }

        binding.btnDone.setOnClickListener {
            val deepLinkUri = Uri.parse("app://com.example.app/dashboard/home")
            val navController = requireView().findNavController()
            val navOptions = androidx.navigation.NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .build()

            navController.navigate(deepLinkUri, navOptions)
                }

        }


    }
