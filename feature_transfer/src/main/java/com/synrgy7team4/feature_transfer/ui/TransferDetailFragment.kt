package com.synrgy7team4.feature_transfer.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferDetailBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessDetailBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessRecipientBinding
import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TransferDetailFragment : Fragment() {
    private var _binding: FragmentTransferDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<TransferViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTransferDetailBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)

        lifecycleScope.launch {
            awaitAll(viewModel.initializeData())
            val id = sharedPreferences.getString("lastidtransaction", "") ?: ""
            viewModel.getMutation(id)
        }
        val destinationBinding = TransSuccessRecipientBinding.bind(binding.recipient.root)
        val transactionSuccessBinding = TransSuccessDetailBinding.bind(binding.transDetail.root)

        viewModel.mutationData.observe(viewLifecycleOwner) { mutationData ->
            destinationBinding.tvRecipientName.text = mutationData.usernameTo
            destinationBinding.tvAccNum.text = mutationData.accountTo
            destinationBinding.tvBank.text = "Lumi Bank"

            binding.tvNominal.text = mutationData.amount.toString()

            transactionSuccessBinding.transDate.text = formatDateTime(mutationData.datetime)
            transactionSuccessBinding.transTime.text = formatHourTime(mutationData.datetime)
        }

        binding.btnDetail.setOnClickListener {
            findNavController().navigate(R.id.action_transferDetailFragment_to_transferSuccessFragment)
        }

        binding.btnShare.setOnClickListener {

        }

        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.action_transferDetailFragment_to_savedAccountFragment)
        }
    }
    private fun formatDateTime(datetime: String?): String {
        val formatterWith6Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val formatterWith5Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS")

        val localDateTime = try {
            LocalDateTime.parse(datetime, formatterWith6Digits)
        } catch (e: Exception) {
            LocalDateTime.parse(datetime, formatterWith5Digits)
        }

        val outputFormatter =
            DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return localDateTime.format(outputFormatter)
    }

    private fun formatHourTime(datetime: String?): String {
        val formatterWith6Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val formatterWith5Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS")

        val localDateTime = try {
            LocalDateTime.parse(datetime, formatterWith6Digits)
        } catch (e: Exception) {
            LocalDateTime.parse(datetime, formatterWith5Digits)
        }

        val dateTimePlus7Hours = localDateTime.plusHours(7)
        val outputFormatter = DateTimeFormatter.ofPattern("HH.mm", Locale("id", "ID"))
        return dateTimePlus7Hours.format(outputFormatter)
    }


}