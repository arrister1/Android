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
import androidx.navigation.findNavController
import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var  sharedPreferences: SharedPrefHelper
    private lateinit var  sharedPreference: SharedPreferences

    private var accountName: String = ""
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String
    private lateinit var fullAccNum: String
    private lateinit var hiddenAccNum: String
    private var isBalanceHidden: Boolean = false
    private var isAccNumHidden: Boolean = false




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreferences = SharedPrefHelper((requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Keluar dari aplikasi
                requireActivity().finishAffinity()
            }
        })

        sharedPreference = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getJwtToken()

        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")

        fullAccNum = getString(R.string.dummy_acc_number)
        hiddenAccNum = formatAccountNumber(fullAccNum)

        if(token != null){
        homeViewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
            binding.tvAccName.text = name
            accountName = name
//            binding.tvName.visibility = View.VISIBLE
//            binding.tvAccName.visibility = View.VISIBLE

        }

        homeViewModel.accountNumber.observe(viewLifecycleOwner) { accountNumber ->
           // binding.tvAccNumber.text = accountNumber
           // binding.tvAccNumber.visibility = View.VISIBLE

            fullAccNum = accountNumber
            hiddenAccNum = formatAccountNumber(fullAccNum)
            binding.tvAccNumber.text = if(isAccNumHidden) hiddenAccNum else fullAccNum
            homeViewModel.fetchBalance(token, accountNumber)

        }

            homeViewModel.balance.observe(viewLifecycleOwner){ balance ->
                fullBalance = "${balance.toString()}"
                hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")
                binding.tvAccBalance.text = if (isBalanceHidden) hiddenBalance else fullBalance            }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        homeViewModel.fetchUserData(token)
        } else{
            Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()

        }


        binding.tvAccBalance.text = fullBalance
        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)

        binding.toggleBalance.setOnClickListener {
            balanceVisibility()
        }
        binding.toggleAcc.setOnClickListener {
            accNumVisibility()
        }

        binding.btnMutasi.setOnClickListener {
            val mutasiNav = Uri.parse("app://com.example.app/mutasi/mutasi")
            requireView().findNavController().navigate(mutasiNav)
        }

        binding.btnTransfer.setOnClickListener {
            val transferNav = Uri.parse("app://com.example.app/trans/transferList")
           requireView().findNavController().navigate(transferNav)

            sharedPreference.edit().apply {
                putString("accountName", accountName)
                putString("accountNo", fullAccNum)
                putString("accountBalance", fullBalance)
                apply()
            }

        }
    }

    private fun accNumVisibility() {
        if(isAccNumHidden){
            binding.tvAccNumber.text = fullAccNum
            binding.toggleAcc.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
            binding.toggleAcc.contentDescription = "Tampilkan  Nomor Rekening"
        } else {
            binding.tvAccNumber.text = hiddenAccNum
            binding.toggleAcc.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
            binding.toggleAcc.contentDescription = "Sembunyikan Nomor Rekening"

        }
        isAccNumHidden = !isAccNumHidden
    }

    private fun formatAccountNumber(accNum: String): String {
        val visibleDigits = 3
        val length = accNum.length
        return if (length > visibleDigits) {
            val hiddenNum = "*".repeat(length-visibleDigits)
            val visibleNum = accNum.takeLast(visibleDigits)
            "$hiddenNum$visibleNum"
        } else {
            accNum
        }

    }

    private fun balanceVisibility() {
        if (isBalanceHidden) {
            binding.tvAccBalance.text = fullBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
            binding.toggleBalance.contentDescription = "Tampilkan Saldo"

        } else {
            binding.tvAccBalance.text = hiddenBalance
            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
            binding.toggleBalance.contentDescription = "Sembunyikan Saldo"

        }
        isBalanceHidden = !isBalanceHidden
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//    private var isHidden: Boolean = false
//    private lateinit var fullBalance: String
//    private lateinit var hiddenBalance: String
//
//    private val homeViewModel: HomeViewModel by viewModel()
//
////    private val viewModel by viewModels<HomeViewModel> {
////
//////        val app = requireActivity().application
//////        (app as MyApplication).viewModelFactory
//////        val app = requireActivity().application as ViewModelFactoryProvider
//////        app.provideViewModelFactory()
////    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
////        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
////        val textView: TextView = binding.tvName
////        homeViewModel.text.observe(viewLifecycleOwner) {
////            textView.text = it
////        }
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // Keluar dari aplikasi
//                requireActivity().finishAffinity()
//            }
//        })
//
//        fullBalance = getString(R.string.dummy_account_balance)
//        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")
//
//        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
//        val getUsername = sharedPreferences.getString("name", null)
//        binding.tvName.text = getUsername ?: "Unknown"
//        binding.tvAccName.text = getUsername ?: "Unknown"
//
//        val getToken = sharedPreferences.getString("token", null)
//        val getAccountNumber = sharedPreferences.getString("accountNumber", null)
//
//        if (getToken != null && getAccountNumber != null) {
//            viewModel.fectData(getToken, getAccountNumber)
//
//            viewModel.data.observe(viewLifecycleOwner) { data ->
//                if (data.success) {
//                    val balanceData = data.data.toString()
//                    binding.tvAccBalance.text = balanceData ?: "0"
//
//                    binding.tvAccNumber.text = getAccountNumber ?: "1234567890"
//
//                    Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else {
////Toast.makeText(requireContext(), "Missing token or account number", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.btnElectric.setOnClickListener {
//            showToast()
//        }
//
//        binding.btnPulsa.setOnClickListener {
//            showToast()
//        }
//
//        binding.btnData.setOnClickListener {
//            showToast()
//        }
//
//        binding.btnEwallet.setOnClickListener {
//            showToast()
//        }
//
//        binding.btnTransfer.setOnClickListener {
//            val transferNav = Uri.parse("app://com.example.app/trans/transferList")
//            requireView().findNavController().navigate(transferNav)
//
//
//        }
//
//        binding.btnMutasi.setOnClickListener {
//
//            val deepLinkUri = Uri.parse("app://com.example.app/mutasi/mutasi")
//            requireView().findNavController().navigate(deepLinkUri)
////            Log.d("HomeFragment", "Attempting to load MutasiFragment")
////
////            val frameLayout = activity?.findViewById<FrameLayout>(R.id.full_framelayout)
////            if (frameLayout != null) {
////                Log.d("HomeFragment", "FrameLayout found: $frameLayout")
////                findNavController().navigate(R.id.action_navigation_home_to_mutasiFragment)
////            } else {
////                Log.e("HomeFragment", "FrameLayout not found")
////                Toast.makeText(requireContext(), "FrameLayout not found", Toast.LENGTH_SHORT).show()
////            }
//        }
////        binding.btnHistory.setOnClickListener {
////            binding.btnHistory.setOnClickListener {
////                findNavController().navigate(R.id.action_navigation_home_to_mutasiFragment)
////            }
//////            val frameLayout = activity?.findViewById<FrameLayout>(R.id.full_framelayout)
//////            if (frameLayout != null) {
//////                activity?.supportFragmentManager?.beginTransaction()?.apply {
//////                    replace(R.id.full_framelayout, MutasiFragment())
//////                    disallowAddToBackStack()
//////                    commit()
//////                }
//////            } else {
//////                Toast.makeText(requireContext(), "FrameLayout not found", Toast.LENGTH_SHORT).show()
//////            }
////        }
//
//        binding.tvAccBalance.text = fullBalance
//        binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
//
//        binding.toggleBalance.setOnClickListener {
//            balanceVisibility()
//        }
//    }
//
//    private fun showToast(duration: Int = Toast.LENGTH_SHORT) {
//        Toast.makeText(requireContext(), "Fitur ini akan segera hadir", duration).show()
//    }
//
//    private fun balanceVisibility() {
//        if (isHidden) {
//            binding.tvAccBalance.text = fullBalance
//            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
//        } else {
//            binding.tvAccBalance.text = hiddenBalance
//            binding.toggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
//        }
//        isHidden = !isHidden
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}