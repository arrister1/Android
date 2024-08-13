package com.synrgy7team4.feature_auth.presentation.register

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.synrgy7team4.common.ViewModelFactoryProvider
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentBiodataBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class BiodataFragment : Fragment() {

    private val binding by lazy { FragmentBiodataBinding.inflate(layoutInflater) }

    private val calendar = Calendar.getInstance()

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel by viewModels<RegisterViewModel> {

        val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_biodata, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnCalendar.setOnClickListener {
            showCalendar()
        }



        binding.btnLanjut.setOnClickListener {
            val deepLinkUri = Uri.parse("app://com.example.app/auth/ktpOnBoard" )

            val ktp = binding.edtKtp.text.toString()
            val name = binding.edtName.text.toString()
            val date = binding.calendar.text.toString()
            val dateFormat = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"

            when {
                ktp.isEmpty() -> binding.edtKtp.error = "No KTP tidak boleh kosong"
                name.isEmpty() -> binding.edtName.error = "Nama tidak boleh kosong"
                date.isEmpty() -> binding.edtName.error = "Tanggal lahir tidak boleh kosong"


                else ->  {

                    if (ktp.length != 16) {
                        binding.edtKtp.error = "NIK harus berjumlah 16 digit"
                    } else if (!date.contains(Regex(dateFormat))) {
                        binding.calendar.error = "Harus berformat dd-mm-yyyy"
                    }
                    else {
                        sharedPreferences.edit().putString("nik", ktp).apply()
                        sharedPreferences.edit().putString("name", name).apply()
                        sharedPreferences.edit().putString("date", date).apply()
                        setToast("Biodata kamu berhasil ditambahkan")
                       // view.findNavController().navigate(R.id.action_biodataFragment_to_pinFragment)
                        view.findNavController().navigate(deepLinkUri)

                    }

//                    sharedPreferences.edit().putString("ktp", ktp).apply()
//                    sharedPreferences.edit().putString("name", name).apply()
//                    setToast("Bidoata kamu berhasil ditambahkan")
//                    view.findNavController().navigate(R.id.action_biodataFragment_to_pinFragment)

                }
            }


        }

        binding.edtKtp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nik = s.toString()
                if (nik.length != 16) {
                    binding.edtKtp.error = "NIK harus berjumlah 16 digit"
                } else {
                    binding.edtKtp.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })



        if(isTalkbackEnabled()){
            binding.edtName.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info?.text = null  // Hapus teks (hint) yang akan dibaca oleh TalkBack
                }
            })
        }

    }

    private fun isTalkbackEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = am.isEnabled
        val isTouchExplorationEnabled = am.isTouchExplorationEnabled
        return isAccessibilityEnabled && isTouchExplorationEnabled

    }

    private fun showCalendar() {
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.CustomDatePickerDialogTheme, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            binding.calendar.setText(formattedDate).toString()
//            sharedPreferences.edit().putString("date", textDate).apply()
        }, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
            )

        datePickerDialog.setOnShowListener {
            // Access the buttons and change their color
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

    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()

    }


}