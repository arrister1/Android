package com.synrgy7team4.feature_transfer.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferPinBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferSuccessBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessDetailBinding
import com.synrgy7team4.feature_transfer.databinding.TransSuccessRecipientBinding
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransferSuccessFragment : Fragment() {
    private var _binding: FragmentTransferSuccessBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentTransferSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnClose.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", "") ?: ""
        val id = sharedPreferences.getString("lastidtransaction", "") ?: ""
        val bankname = sharedPreferences.getString("bankname", "") ?: "Lumi Bank"
        transferViewModel.getMutation(token, id)

        transferViewModel.mutationData.observe(viewLifecycleOwner) { mutationData ->
            mutationData?.let {
                binding.layoutRecipient.tvName.text = it.usernameTo
                binding.layoutRecipient.tvBankNum.text = it.accountTo
                binding.layoutRecipient.tvBankName.text = bankname

                binding.layoutSender.tvSenderName.text = it.usernameFrom
                binding.layoutSender.tvAccNum.text = it.accountFrom

                binding.layoutTransDetail.tvTransTotal.text = "Rp. ${it.amount}"

                binding.transStatus.tvTransDate.text = formatDateTime(it.datetime)
                binding.transStatus.tvTransTime.text = formatHourTime(it.datetime)
            }}


    }

    fun formatDateTime(datetime: String): String {
        val formatterWith6Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val formatterWith5Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS")

        val localDateTime = try {
            LocalDateTime.parse(datetime, formatterWith6Digits)
        } catch (e: Exception) {
            LocalDateTime.parse(datetime, formatterWith5Digits)
        }
        val dateTimePlus7Hours = localDateTime.plusHours(7)
        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return dateTimePlus7Hours.format(outputFormatter).plus(7)
    }

    fun formatHourTime(datetime: String): String {
        val formatterWith6Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val formatterWith5Digits = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS")

        val localDateTime = try {
            LocalDateTime.parse(datetime, formatterWith6Digits)
        } catch (e: Exception) {
            LocalDateTime.parse(datetime, formatterWith5Digits)
        }
        val dateTimePlus7Hours = localDateTime.plusHours(7)
        val outputFormatter = DateTimeFormatter.ofPattern("HH.mm", Locale("id", "ID"))
        return dateTimePlus7Hours.format(outputFormatter).plus(7)
    }


}