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
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.databinding.FragmentCheckReceiverDetailBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentFellowAccountBankInputBinding
import com.synrgy7team4.feature_transfer.databinding.FragmentSavedAccountBinding
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavedAccountFragment : Fragment() {
    private var _binding: FragmentSavedAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPrefHelper: SharedPrefHelper

    private lateinit var adapter: CustomAdapter
    private val viewModel: TransferViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedAccountBinding.inflate(inflater, container, false)
        sharedPrefHelper = SharedPrefHelper((requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        val recyclerview = view.findViewById<RecyclerView>(R.id.saved_account_list)
        recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = CustomAdapter(requireContext(), mutableListOf())
        recyclerview.adapter = adapter

        // Fetch token from SharedPreferences
        val token = sharedPrefHelper.getJwtToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()
            return
        }

        // Observe account list data from ViewModel
        viewModel.accountList.observe(viewLifecycleOwner) { response ->
            response?.data?.let { dataList ->
                val sortedList = dataList.sortedBy { it.name }
                val transformedList = transformSortedListToMutableList(sortedList)
                adapter.updateList(transformedList)
            }
        }

        // Fetch account list data from API
        if (!token.isNullOrEmpty()) {
            viewModel.getAccountList(token)
        } else {
            Toast.makeText(requireContext(), "Token not found!", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnClickListener(object : CustomAdapter.OnClickListener {
            override fun onClick(position: Int, model: AccountData) {
                sharedPreferences.edit().apply {
                    putString("accountDestinationName", model.name)
                    putString("accountDestinationNo", model.accountNumber)
                   // putString("accountDestinationBankName", model.id)
                    apply()
                }

                val transferInputNav = Uri.parse("app://com.example.app/trans/transferInput")
                Toast.makeText(requireContext(), "${model.name} ${model.accountNumber} ${model.id}", Toast.LENGTH_SHORT).show()
                requireView().findNavController().navigate(transferInputNav)
            }
        })

        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(msg: String): Boolean {
                //filter(msg)
                return false
            }
        })

        view.findViewById<MaterialButton>(R.id.sameBankButton).setOnClickListener { handleSameBankButtonClick(view) }
        view.findViewById<MaterialButton>(R.id.differentBankButton).setOnClickListener { handleDifferentBankButtonClick(view) }
        binding.addNewAccountInfo.setOnClickListener {
            val addNewAccNav = Uri.parse("app://com.example.app/trans/accInput")
            requireView().findNavController().navigate(addNewAccNav)
        }

        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun transformSortedListToMutableList(list: List<AccountData>): MutableList<Any> {
        val mutableList = mutableListOf<Any>()
        var currentSection = ""

        for (account in list) {
            val firstLetter = account.name[0].toString().uppercase()
            if (currentSection != firstLetter) {
                currentSection = firstLetter
                mutableList.add(firstLetter)
            }
            mutableList.add(account)
        }

        return mutableList
    }

    private fun handleSameBankButtonClick(view: View) {
        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
        sameBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
        sameBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))

        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
        differentBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
        differentBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
    }

    private fun handleDifferentBankButtonClick(view: View) {
        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
        differentBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
        differentBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))

        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
        sameBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
        sameBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
    }
}


//class SavedAccountFragment : Fragment() {
//    private var _binding: FragmentSavedAccountBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var sharedPrefHelper: SharedPrefHelper
//
//
//
//    private lateinit var adapter: CustomAdapter
//    private val viewModel: TransferViewModel by viewModel()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentSavedAccountBinding.inflate(inflater, container, false)
//        sharedPrefHelper = SharedPrefHelper((requireContext()))
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
//
//        val recyclerview = view.findViewById<RecyclerView>(R.id.saved_account_list)
//        recyclerview.layoutManager = LinearLayoutManager(context)
//        adapter = CustomAdapter(requireContext(), mutableListOf())
//        recyclerview.adapter = adapter
//
//        val token = sharedPrefHelper.getJwtToken()
//        if (token == null) {
//            Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Observe account list data from ViewModel
//        viewModel.getAccountList(token).observe(viewLifecycleOwner) { response ->
//            // Transform and set data in adapter
//            val sortedList = response.data!!.sortedBy { it.name }
//            val transformedList = transformSortedListToMutableList(sortedList)
//            adapter.updateList(transformedList)
//        }
//
//        // Fetch account list data from API
//        if (!token.isNullOrEmpty()) {
//            viewModel.getAccountList(token)
//        } else {
//            Toast.makeText(requireContext(), "Token not found!", Toast.LENGTH_SHORT).show()
//        }
//
//        adapter.setOnClickListener(object : CustomAdapter.OnClickListener {
//            override fun onClick(position: Int, model: AccountData) {
//                sharedPreferences.edit().putString("accountDestinationName", model.name).apply()
//                sharedPreferences.edit().putString("accountDestinationNo", model.accountNumber).apply()
//                sharedPreferences.edit().putString("accountDestinationBankName", model.id).apply()
//
//                val transferInputNav = Uri.parse("app://com.example.app/trans/transferInput")
//                Toast.makeText(requireContext(), "${model.name} ${model.accountNumber} ${model.id}", Toast.LENGTH_SHORT).show()
//                requireView().findNavController().navigate(transferInputNav)
//            }
//        })
//
//        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean = false
//
//            override fun onQueryTextChange(msg: String): Boolean {
//                //filter(msg)
//                return false
//            }
//        })
//
//        view.findViewById<MaterialButton>(R.id.sameBankButton).setOnClickListener { handleSameBankButtonClick(view) }
//        view.findViewById<MaterialButton>(R.id.differentBankButton).setOnClickListener { handleDifferentBankButtonClick(view) }
//        binding.addNewAccountInfo.setOnClickListener {
//            val addNewAccNav = Uri.parse("app://com.example.app/trans/accInput")
//            requireView().findNavController().navigate(addNewAccNav)
//        }
//
//        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
//            view.findNavController().popBackStack()
//        }
//    }
//
////    private fun filter(text: String) {
////        val filteredList = (adapter.getData() as List<SavedAccountDataObject>).filter {
////            it.name.lowercase().contains(text.lowercase())
////        }
////        adapter.updateList(transformSortedListToMutableList(filteredList))
////    }
//
//    private fun transformSortedListToMutableList(list: List<AccountData>): MutableList<Any> {
//        val mutableList = mutableListOf<Any>()
//        var currentSection = ""
//
//        for (account in list) {
//            val firstLetter = account.name[0].toString().uppercase()
//            if (currentSection != firstLetter) {
//                currentSection = firstLetter
//                mutableList.add(firstLetter)
//            }
//            mutableList.add(account)
//        }
//
//        return mutableList
//    }
//
//    private fun handleSameBankButtonClick(view: View) {
//        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
//        sameBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
//        sameBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
//
//        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
//        differentBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
//        differentBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
//    }
//
//    private fun handleDifferentBankButtonClick(view: View) {
//        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
//        differentBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
//        differentBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
//
//        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
//        sameBankButton.setBackgroundColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.white))
//        sameBankButton.setTextColor(ContextCompat.getColor(requireContext(), com.synrgy7team4.common.R.color.primary_blue))
//    }
//}
//class SavedAccountFragment : Fragment() {
//    private var _binding: FragmentSavedAccountBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var sharedPrefHelper: SharedPrefHelper
//    private lateinit var adapter: CustomAdapter
//    private val transferViewModel: TransferViewModel by viewModel()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentSavedAccountBinding.inflate(inflater, container, false)
//        sharedPrefHelper = SharedPrefHelper(requireContext())
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val token = sharedPrefHelper.getJwtToken()
//        if (token == null) {
//            Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Initialize RecyclerView and Adapter
//        adapter = CustomAdapter(requireContext(), mutableListOf())
//        binding.savedAccountList.layoutManager = LinearLayoutManager(context)
//        binding.savedAccountList.adapter = adapter
//
//        // Observe the account list from ViewModel
//        transferViewModel.accountList.observe(viewLifecycleOwner) { accountList ->
//            val sortedList = accountList.sortedBy { it.accountName }
//            val transformedList = transformSortedListToMutableList(sortedList)
//            adapter.filterList(transformedList)
//        }
//
//        // Observe error state
//        transferViewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
//            if (errorMsg != null) {
//                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
//            }
//        }
//
//        // Set click listener for the adapter
//        adapter.setOnClickListener(object : CustomAdapter.OnClickListener {
//            override fun onClick(position: Int, model: SavedAccountDataObject) {
//                sharedPrefHelper.saveJwtToken(token) // Save token
//                sharedPrefHelper.edit().putString("accountDestinationName", model.accountName).apply()
//                sharedPrefHelper.edit().putString("accountDestinationNo", model.accountNo).apply()
//                sharedPrefHelper.edit().putString("accountDestinationBankName", model.bankName).apply()
//
//                findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
//            }
//        })
//
//        // Handle search functionality
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean = false
//
//            override fun onQueryTextChange(msg: String): Boolean {
//                filter(msg)
//                return false
//            }
//        })
//
//        // Handle button clicks for filtering by bank type
//        binding.sameBankButton.setOnClickListener { handleSameBankButtonClick(view) }
//        binding.differentBankButton.setOnClickListener { handleDifferentBankButtonClick(view) }
//        binding.addNewAccountInfo.setOnClickListener {
//            findNavController().navigate(Uri.parse("app://com.example.app/trans/accInput"))
//        }
//
//        binding.btnBack.setOnClickListener {
//            view.findNavController().popBackStack()
//        }
//
//        // Fetch account list when the view is created
//        transferViewModel.getAccountList(token)
//    }
//
//    private fun filter(text: String) {
//        val filteredList = transferViewModel.accountList.value?.filter {
//            it.accountName.contains(text, ignoreCase = true)
//        } ?: emptyList()
//
//        adapter.filterList(transformSortedListToMutableList(filteredList))
//    }
//
//    private fun transformSortedListToMutableList(list: List<SavedAccountDataObject>): MutableList<Any> {
//        val mutableList = mutableListOf<Any>()
//        var currentSection = ""
//
//        for (account in list) {
//            val firstLetter = account.accountName[0].toString().uppercase()
//            if (currentSection != firstLetter) {
//                currentSection = firstLetter
//                mutableList.add(firstLetter)
//            }
//            mutableList.add(account)
//        }
//
//        return mutableList
//    }
//
//    private fun handleSameBankButtonClick(view: View) {
//        binding.sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue))
//        binding.sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//
//        binding.differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//        binding.differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue))
//    }
//
//    private fun handleDifferentBankButtonClick(view: View) {
//        binding.differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue))
//        binding.differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//
//        binding.sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//        binding.sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue))
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//

//class SavedAccountFragment : Fragment() {
//    private var _binding: FragmentSavedAccountBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var sharedPreferences: SharedPreferences
//
//    private lateinit var adapter: CustomAdapter
//
//    private var dummyData = listOf<SavedAccountDataObject>(
//        SavedAccountDataObject(123, "account 1", "BCA", "123125412"),
//        SavedAccountDataObject(123, "bccount 4", "MayBank", "123434645"),
//        SavedAccountDataObject(123, "account 2", "BRI", "1244325"),
//        SavedAccountDataObject(123, "cccount 5", "Panin", "12346696"),
//        SavedAccountDataObject(123, "cccount 6", "MayBank", "986745623"),
//        SavedAccountDataObject(123, "bccount 3", "BNI", "8679875634"),
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentSavedAccountBinding.inflate(inflater, container, false)
//        return binding.root}
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
//
//        sortData();
//
//        val recyclerview = view.findViewById<RecyclerView>(R.id.saved_account_list)
//        recyclerview.layoutManager = LinearLayoutManager(context);
//        adapter = CustomAdapter(requireContext(), transformSortedListToMutableList(dummyData))
//        recyclerview.adapter = adapter
//
//        adapter.setOnClickListener(object :
//            CustomAdapter.OnClickListener {
//            override fun onClick(position: Int, model: SavedAccountDataObject) {
//                sharedPreferences.edit().putString("accountDestinationName", model.accountName).apply()
//                sharedPreferences.edit().putString("accountDestinationNo", model.accountNo).apply()
//                sharedPreferences.edit().putString("accountDestinationBankName", model.bankName).apply()
//
//                val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
//                Toast.makeText(requireContext(), "${model.accountName} ${model.accountNo} ${model.bankName}", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
////                requireView().findNavController().navigate(transferInputNav)
//
//            }
//        })
//
//        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(msg: String): Boolean {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filter(msg)
//                return false
//            }
//        })
//
//        view.findViewById<MaterialButton>(R.id.sameBankButton).setOnClickListener {handleSameBankButtonClick(view)}
//        view.findViewById<MaterialButton>(R.id.differentBankButton).setOnClickListener {handleDifferentBankButtonClick(view)}
//        binding.addNewAccountInfo.setOnClickListener {
//            val addNewAccNav = Uri.parse(  "app://com.example.app/trans/accInput")
//            //handleAddNewAccountInfoClick()
//            requireView().findNavController().navigate(addNewAccNav)
//
//        }
//
//        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
//            view.findNavController().popBackStack()
//        }
//
//
//    }
//
//    private fun filter(text: String) {
//        // creating a new array list to filter our data.
//        val filteredList = arrayListOf<SavedAccountDataObject>();
//
//        // running a for loop to compare elements.
//        for (item in dummyData) {
//            item.accountName
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.accountName.lowercase().contains(text.lowercase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredList.add(item)
//            }
//        }
//
//        adapter.filterList(transformSortedListToMutableList(filteredList))
//    }
//
//    private fun sortData()
//    {
//        val sortedList = dummyData.sortedBy { it.accountName }
//        dummyData = sortedList
//    }
//
//    private fun transformSortedListToMutableList(list: List<SavedAccountDataObject>): MutableList<Any>
//    {
//        val mutableList = mutableListOf<Any>();
//        var currentSection = "";
//
//        for (account in list)
//        {
//            val firstLetter = account.accountName[0].toString().uppercase();
//            if (currentSection != firstLetter)
//            {
//                currentSection = firstLetter
//                mutableList.add(firstLetter)
//            }
//            mutableList.add(account)
//        }
//
//        return mutableList
//    }
//
//    private fun handleSameBankButtonClick(view:View)
//    {
//        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
//        sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
//        sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//
//        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
//        differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white));
//        differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
//    }
//
//    private fun handleDifferentBankButtonClick(view:View)
//    {
//        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
//        differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
//        differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))
//
//        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
//        sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white));
//        sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
//    }
//
//    private fun handleAddNewAccountInfoClick()
//    {
//        Toast.makeText(requireContext(), "add new account !", Toast.LENGTH_SHORT).show()
//    }
//}