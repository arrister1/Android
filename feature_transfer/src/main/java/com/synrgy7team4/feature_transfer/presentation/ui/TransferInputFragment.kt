package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.synrgy7team4.feature_transfer.R

class TransferInputFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_transfer_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.submitForm).setOnClickListener {handleSubmitFormClick(view)}

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun handleSubmitFormClick(view:View)
    {
        val nominal = view.findViewById<TextInputEditText>(R.id.amountInputText).text.toString()
        val berita = view.findViewById<TextInputEditText>(R.id.inputNote).text

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