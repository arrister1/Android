package com.synrgy7team4.feature_mutasi.ui.mutasiScreen

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_mutasi.R
import com.synrgy7team4.feature_mutasi.databinding.FragmentMutasiBinding
import com.synrgy7team4.feature_mutasi.viewmodel.MutasiViewmodel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MutasiFragment : Fragment() {
    private var _binding: FragmentMutasiBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MutasiViewmodel>()
    private lateinit var adapter: MutationPerDateAdapter

    private var startDate: String? = null
    private var endDate: String? = null
    private var transactionType: String = "Semua"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMutasiBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMutationData(startDate, endDate)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tilTanggalAwal.setEndIconOnClickListener {
            showDateEndPickerDialog(binding.tvTangalAwal) { selectedStartDate ->
                binding.tiedtTanggalAwal.setText(selectedStartDate)
                startDate = selectedStartDate

                if (endDate != null) {
                    viewModel.getMutationData(startDate, endDate)
                }
            }
        }

        binding.tilTanggalAkhir.setEndIconOnClickListener {
            showDateEndPickerDialog(binding.tvTangalAkhir) { selectedEndDate ->
                binding.tiedtTanggalAkhir.setText(selectedEndDate)
                endDate = selectedEndDate

                if (startDate != null) {
                    viewModel.getMutationData(startDate, endDate)
                }
            }
        }

        val transaksi = resources.getStringArray(R.array.spinner_jenis_transaksi)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transaksi)
        binding.spinner.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when (position) {
                        0 -> transactionType = "Semua"
                        1 -> transactionType = "Uang Masuk"
                        2 -> transactionType = "Uang Keluar"
                    }
                    viewModel.getMutationData(startDate, endDate)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.mutationData.observe(viewLifecycleOwner) { (mutations, accountNumber) ->
            mutations?.let {
                val filteredMutation = viewModel.filterMutationByType(mutations, transactionType)
                accountNumber?.let {
                    adapter = MutationPerDateAdapter(filteredMutation, accountNumber)
                    binding.rvMutasi.adapter = adapter
                    binding.rvMutasi.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }
    }

    private fun showDateEndPickerDialog(textView: TextView, onDateSelected: (String) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDateMillis = selection ?: return@addOnPositiveButtonClickListener
            val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(selectedDateMillis))

            onDateSelected(selectedDate)
        }
        datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}