package com.synrgy7team4.feature_transfer.presentation.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferInputBinding

class TransferInputFragment : Fragment() {
    private var _binding: FragmentTransferInputBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransferInputBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

        //return inflater.inflate(R.layout.fragment_transfer_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.submitForm.setOnClickListener{
           handleSubmitFormClick(view)

           val pinNav = Uri.parse("app://com.example.app/trans/transferPin")
           requireView().findNavController().navigate(pinNav)

       }

       binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun handleSubmitFormClick(view:View)
    {
        val nominal = binding.amountInputText.text.toString()
        val berita = binding.inputNote.text

        if(!validateAmount(nominal))
            return

        Toast.makeText(requireContext(), "$nominal $berita", Toast.LENGTH_SHORT).show()
    }

    private fun validateAmount(amount:String): Boolean{
        if(amount.isEmpty())
        {
            Toast.makeText(requireContext(), "nominal harus di isi !", Toast.LENGTH_SHORT).show()
            return false
        }

        val nominal = Integer.parseInt(amount)

        if(nominal < 10000)
        {
            Toast.makeText(requireContext(), "nominal harus di atas 10000 !", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}