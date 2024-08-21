package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.databinding.FragmentTransferInputBinding
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransferInputFragment : Fragment() {
    private var _binding: FragmentTransferInputBinding? = null
    private val binding get() = _binding!!
    private val transferViewModel: TransferViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    //private var accnumb: String = "0000000"


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

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val accountDestinationName = sharedPreferences.getString("accountDestinationName", null)
        val accountDestinationNo = sharedPreferences.getString("accountDestinationNo", null)
        val accountDestinationBankName = sharedPreferences.getString("accountDestinationBankName", null)

        binding.accountName.text = accountDestinationName
        binding.bankNameAndAccountNo.text = "${accountDestinationBankName} - ${accountDestinationNo}"

        transferViewModel.getUserData()

        transferViewModel.userDataResult.observe(viewLifecycleOwner) { user ->
            binding.userAccountName.text = user.data?.name ?: "User"
            binding.userAccountNo.text = user.data?.accountNumber ?: "0000000"
            val accnumb = user.data?.accountNumber
            if (accnumb != null) {
                transferViewModel.getBalance(accnumb)
            }
        }


        transferViewModel.balanceResult.observe(viewLifecycleOwner){ user ->
            binding.userAccountBalance.text = user.data.toString()
        }


        binding.submitForm.setOnClickListener{
           handleSubmitFormClick(view)

           val pinNav = Uri.parse("app://com.example.app/trans/transferPin")
            requireView().findNavController().navigate(pinNav)
         //  requireView().findNavController().navigate(R.id.action_transferInputFragment_to_transferPinFragment)
       }

       binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun handleSubmitFormClick(view:View)
    {
        val nominal = binding.amountInputText.text.toString()
        val berita = binding.inputNote.text.toString()

        sharedPreferences.edit().putInt("transferAmount", nominal.toInt()).apply()
        sharedPreferences.edit().putString("transferDescription", berita).apply()

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