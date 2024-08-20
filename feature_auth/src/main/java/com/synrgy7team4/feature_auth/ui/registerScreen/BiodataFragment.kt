package com.synrgy7team4.feature_auth.ui.registerScreen

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentBiodataBinding
import com.synrgy7team4.feature_auth.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BiodataFragment : Fragment() {
    private var _binding: FragmentBiodataBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var sharedPreferences: SharedPreferences
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentBiodataBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnCalendar.setOnClickListener {
            showCalendar()
        }

        binding.btnLanjut.setOnClickListener {
            val ktp = binding.edtKtp.text.toString()
            val name = binding.edtName.text.toString()
            when {
                ktp.isEmpty() -> binding.edtKtp.error = "No KTP tidak boleh kosong"
                name.isEmpty() -> binding.edtName.error = "Nama tidak boleh kosong"

                else -> {
                    if (ktp.length != 16) {
                        binding.edtKtp.error = "NIK harus berjumlah 16 digit"
                    } else {
                        viewModel.checkKtpNumberAvailability(ktp)
                    }
                }
            }
        }

        binding.edtKtp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nik = s.toString()
                if (nik.length != 16) {
                    binding.edtKtp.error = "NIK harus berjumlah 16 digit"
                } else {
                    binding.edtKtp.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.isKtpNumberAvailable.observe(viewLifecycleOwner) { isKtpNumberAvailable ->
            if (isKtpNumberAvailable) {
                val ktp = binding.edtKtp.text.toString()
                val name = binding.edtName.text.toString()
                sharedPreferences.edit().putString("nik", ktp).apply()
                sharedPreferences.edit().putString("name", name).apply()
                makeToast(requireContext(), "Biodata kamu berhasil ditambahkan")
                view.findNavController().navigate(R.id.action_biodataFragment_to_ktpVerificationBoardFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }

        if (isTalkbackEnabled()) {
            binding.edtName.accessibilityDelegate = object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfo
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
                }
            }
        }
    }

    private fun isTalkbackEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = am.isEnabled
        val isTouchExplorationEnabled = am.isTouchExplorationEnabled

        return isAccessibilityEnabled && isTouchExplorationEnabled
    }

    private fun showCalendar() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialogTheme,
            { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.calendar.text = formattedDate
                sharedPreferences.edit().putString("date", formattedDate).apply()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.setOnShowListener {
            val positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
            val negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)

            val positiveColor = ContextCompat.getColor(requireContext(), R.color.primary_blue)
            val negativeColor = ContextCompat.getColor(requireContext(), R.color.primary_darkBlue)

            positiveButton.setTextColor(positiveColor)
            negativeButton.setTextColor(negativeColor)
        }

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}