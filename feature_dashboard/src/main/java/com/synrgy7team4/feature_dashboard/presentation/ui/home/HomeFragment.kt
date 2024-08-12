package com.synrgy7team4.feature_dashboard.presentation.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isHidden: Boolean = false
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String
//    private val viewModel by viewModels<HomeViewModel> {
//
////        val app = requireActivity().application
////        (app as MyApplication).viewModelFactory
////        val app = requireActivity().application as ViewModelFactoryProvider
////        app.provideViewModelFactory()
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.tvName
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Keluar dari aplikasi
                requireActivity().finishAffinity()
            }
        })

        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val getUsername = sharedPreferences.getString("name", null)
        binding.tvName.text = getUsername ?: "Unknown"
        binding.tvAccName.text = getUsername ?: "Unknown"

        val getToken = sharedPreferences.getString("token", null)
        val getAccountNumber = sharedPreferences.getString("accountNumber", null)

        if (getToken != null && getAccountNumber != null) {
            viewModel.fectData(getToken, getAccountNumber)

            viewModel.data.observe(viewLifecycleOwner) { data ->
                if (data.success) {
                    val balanceData = data.data.toString()
                    binding.tvAccBalance.text = balanceData ?: "0"

                    binding.tvAccNumber.text = getAccountNumber ?: "1234567890"

                    Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
//Toast.makeText(requireContext(), "Missing token or account number", Toast.LENGTH_SHORT).show()
        }

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
            val transferNav = Uri.parse("app://com.example.app/trans/transferList")
            requireView().findNavController().navigate(transferNav)


        }

        binding.btnMutasi.setOnClickListener {

            val deepLinkUri = Uri.parse("app://com.example.app/mutasi/mutasi")
            requireView().findNavController().navigate(deepLinkUri)
//            Log.d("HomeFragment", "Attempting to load MutasiFragment")
//
//            val frameLayout = activity?.findViewById<FrameLayout>(R.id.full_framelayout)
//            if (frameLayout != null) {
//                Log.d("HomeFragment", "FrameLayout found: $frameLayout")
//                findNavController().navigate(R.id.action_navigation_home_to_mutasiFragment)
//            } else {
//                Log.e("HomeFragment", "FrameLayout not found")
//                Toast.makeText(requireContext(), "FrameLayout not found", Toast.LENGTH_SHORT).show()
//            }
        }
//        binding.btnHistory.setOnClickListener {
//            binding.btnHistory.setOnClickListener {
//                findNavController().navigate(R.id.action_navigation_home_to_mutasiFragment)
//            }
////            val frameLayout = activity?.findViewById<FrameLayout>(R.id.full_framelayout)
////            if (frameLayout != null) {
////                activity?.supportFragmentManager?.beginTransaction()?.apply {
////                    replace(R.id.full_framelayout, MutasiFragment())
////                    disallowAddToBackStack()
////                    commit()
////                }
////            } else {
////                Toast.makeText(requireContext(), "FrameLayout not found", Toast.LENGTH_SHORT).show()
////            }
//        }

        binding.tvAccBalance.text = fullBalance
        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)

        binding.toggleBalance.setOnClickListener {
            balanceVisibility()
        }
    }

    private fun showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), "Fitur ini akan segera hadir", duration).show()
    }

    private fun balanceVisibility() {
        if (isHidden) {
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