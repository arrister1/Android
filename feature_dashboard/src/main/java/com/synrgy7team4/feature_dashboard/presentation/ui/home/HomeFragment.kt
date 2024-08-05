package com.synrgy7team4.feature_dashboard.presentation.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jer.shared.ViewModelFactoryProvider
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels{  val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()}

    private var isHidden: Boolean = false
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.tvName

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")



        binding.btnElectric.setOnClickListener {
            showToast()
        }

        binding.btnPulsa.setOnClickListener {
            showToast()
        }

        binding.btnData.setOnClickListener {
            showToast()
        }

        binding.btnEwallet.setOnClickListener {
            showToast()
        }

        binding.btnTransfer.setOnClickListener {
            showToast()
        }

        binding.btnHistory.setOnClickListener {
            showToast()
        }

        binding.tvAccBalance.text = fullBalance
        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)

        binding.toggleBalance.setOnClickListener{
            balanceVisibility()
        }
    }

    private fun showToast( duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), "Fitur ini akan segera hadir", duration).show()
    }


    private fun balanceVisibility() {
        if(isHidden){
            binding.tvAccBalance.text = fullBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
        } else {
            binding.tvAccBalance.text = hiddenBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
        }
        isHidden = !isHidden

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}