package com.synrgy7team4.feature_auth.presentation.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jer.shared.ViewModelFactoryProvider
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentBiodataBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel


class BiodataFragment : Fragment() {

    private val binding by lazy { FragmentBiodataBinding.inflate(layoutInflater) }

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

        binding.btnLanjut.setOnClickListener {
            val ktp = binding.edtKtp.text.toString()
            val name = binding.edtName.text.toString()

            when {
                ktp.isEmpty() -> binding.edtKtp.error = "No KTP tidak boleh kosong"
                name.isEmpty() -> binding.edtName.error = "Nama tidak boleh kosong"
                else ->  {
                    sharedPreferences.edit().putString("ktp", ktp).apply()
                    sharedPreferences.edit().putString("name", name).apply()
                    setToast("Bidoata kamu berhasil ditambahkan")
                    view.findNavController().navigate(R.id.action_biodataFragment_to_ktpVerificationBoardFragment)

                }
            }
        }



    }

    private fun setToast(msg: String) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()

    }


}