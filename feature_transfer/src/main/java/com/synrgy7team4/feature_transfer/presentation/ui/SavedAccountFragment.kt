package com.synrgy7team4.feature_transfer.presentation.ui.transfer

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.synrgy7team4.feature_transfer.R

class SavedAccountFragment : Fragment() {

    lateinit var adapter: CustomAdapter

    private var dummyData = listOf<SavedAccountDataObject>(
        SavedAccountDataObject(123, "account 1", "BCA", "123125412"),
        SavedAccountDataObject(123, "bccount 4", "MayBank", "123434645"),
        SavedAccountDataObject(123, "account 2", "BRI", "1244325"),
        SavedAccountDataObject(123, "cccount 5", "Panin", "12346696"),
        SavedAccountDataObject(123, "cccount 6", "MayBank", "986745623"),
        SavedAccountDataObject(123, "bccount 3", "BNI", "8679875634"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortData();

        val recyclerview = view.findViewById<RecyclerView>(R.id.saved_account_list)
        recyclerview.layoutManager = LinearLayoutManager(context);
        adapter = CustomAdapter(requireContext(), transformSortedListToMutableList(dummyData))
        recyclerview.adapter = adapter

        adapter.setOnClickListener(object :
            CustomAdapter.OnClickListener {
            override fun onClick(position: Int, model: SavedAccountDataObject) {
                val transferInputNav = Uri.parse(  "app://com.example.app/trans/transferInput")
                Toast.makeText(requireContext(), "${model.accountName} ${model.accountNo} ${model.bankName}", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
                requireView().findNavController().navigate(transferInputNav)

            }
        })

        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })

        view.findViewById<MaterialButton>(R.id.sameBankButton).setOnClickListener {handleSameBankButtonClick(view)}
        view.findViewById<MaterialButton>(R.id.differentBankButton).setOnClickListener {handleDifferentBankButtonClick(view)}
        view.findViewById<MaterialButton>(R.id.addNewAccountInfo).setOnClickListener {handleAddNewAccountInfoClick()}

        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList = arrayListOf<SavedAccountDataObject>();

        // running a for loop to compare elements.
        for (item in dummyData) {
            item.accountName
            // checking if the entered string matched with any item of our recycler view.
            if (item.accountName.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }

        adapter.filterList(transformSortedListToMutableList(filteredList))
    }

    private fun sortData()
    {
        val sortedList = dummyData.sortedBy { it.accountName }
        dummyData = sortedList
    }

    private fun transformSortedListToMutableList(list: List<SavedAccountDataObject>): MutableList<Any>
    {
        val mutableList = mutableListOf<Any>();
        var currentSection = "";

        for (account in list)
        {
            val firstLetter = account.accountName[0].toString().uppercase();
            if (currentSection != firstLetter)
            {
                currentSection = firstLetter
                mutableList.add(firstLetter)
            }
            mutableList.add(account)
        }

        return mutableList
    }

    private fun handleSameBankButtonClick(view:View)
    {
        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
        sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
        sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))

        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
        differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white));
        differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
    }

    private fun handleDifferentBankButtonClick(view:View)
    {
        val differentBankButton = view.findViewById<MaterialButton>(R.id.differentBankButton)
        differentBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
        differentBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.white))

        val sameBankButton = view.findViewById<MaterialButton>(R.id.sameBankButton)
        sameBankButton.setBackgroundColor(resources.getColor(com.synrgy7team4.common.R.color.white));
        sameBankButton.setTextColor(resources.getColor(com.synrgy7team4.common.R.color.primary_blue));
    }

    private fun handleAddNewAccountInfoClick()
    {
        Toast.makeText(requireContext(), "add new account !", Toast.LENGTH_SHORT).show()
    }
}