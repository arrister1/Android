package com.synrgy7team4.feature_transfer.ui.fromQR

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferSuccessFromQrBinding
import com.synrgy7team4.feature_transfer.viewmodel.TransferViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class TransferSuccessFromQrFragment : Fragment() {

    private var _binding: FragmentTransferSuccessFromQrBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransferViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentTransferSuccessFromQrBinding.inflate(layoutInflater).also{
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            awaitAll(viewModel.initializeData())
            val id = sharedPreferences.getString("lastidtransactionFromQr", "") ?: ""
            viewModel.getMutation(id)
        }

        binding.btnClose.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        viewModel.mutationData.observe(viewLifecycleOwner) { mutationData ->
            mutationData?.let {
                binding.layoutRecipient.tvName.text = mutationData.usernameTo
                binding.layoutRecipient.tvBankNum.text = mutationData.accountTo
                binding.layoutRecipient.tvBankName.text = "Lumi Bank"

                binding.layoutSender.tvSenderName.text = mutationData.usernameFrom
                binding.layoutSender.tvAccNum.text = mutationData.accountFrom

                binding.layoutTransDetail.tvTransTotal.text = "Rp. ${mutationData.amount}"
                binding.transStatus.tvTransDate.text = formatDateTime(mutationData.datetime)
                binding.transStatus.tvTransTime.text = formatHourTime(mutationData.datetime)
            }
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

        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))
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

        val outputFormatter = DateTimeFormatter.ofPattern("HH.mm", Locale("id", "ID"))
        return localDateTime.format(outputFormatter)
    }


}