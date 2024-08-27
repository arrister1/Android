package com.synrgy7team4.feature_transfer.ui.savedAccountScreen

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountGetDataDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountsGetResponseDomain
import com.synrgy7team4.feature_transfer.R

class SavedAccountAdapter(
    private val savedAccountData: MutableList<Any>,
    private val sharedPreferences: SharedPreferences,
    private val context: Context, private var mList: MutableList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var filteredList: MutableList<Any> = mList.toMutableList()

//    fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val query = constraint?.toString()?.lowercase() ?: ""
//
//                val newList = if (query.isEmpty()) {
//                    mList
//                } else {
//                    val tempList = mutableListOf<Any>()
//                    var currentSection: String? = null
//                    var sectionHasMatches = false
//
//                    for (item in mList) {
//                        if (item is String) {
//                            /*// This is a section header
//                            if (sectionHasMatches) {
//                                // Add the previous section header if it had matches
//                                tempList.add(currentSection!!)
//                            }*/
//                            currentSection = item
//                            sectionHasMatches = false
//                        } else if (item is SavedAccountGetDataDomain) {
//                            if (item.name!!.lowercase().contains(query) ||
//                                item.accountNumber!!.contains(query) ||
//                                item.id!!.contains(query)) {
//                                if (!sectionHasMatches) {
//                                    // Add the section header if this is the first match
//                                    tempList.add(currentSection!!)
//                                    sectionHasMatches = true
//                                }
//                                tempList.add(item)
//                            }
//                        }
//                    }
//                    tempList
//                }
//
//                val filterResults = FilterResults()
//                filterResults.values = newList
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredList = results?.values as MutableList<Any>
//                notifyDataSetChanged()
//            }
//        }
//    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    mList
                } else {
                    mList.filter { item ->
                        when (item) {
                            is SavedAccountGetDataDomain -> {
                                item.name?.lowercase()?.contains(query) == true ||
                                        item.accountNumber?.contains(query) == true
                            }
                            else -> false
                        }
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                savedAccountData.clear()
                savedAccountData.addAll(results?.values as List<Any>)
                notifyDataSetChanged()
            }
        }
    }
    fun updateData(newList: MutableList<Any>) {
        savedAccountData.clear()
        savedAccountData.addAll(newList)
        mList = newList
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int = if (savedAccountData[position] is String) 0 else 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            AccountInfoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.account_info_card, parent, false)
            )
        } else {
            SectionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.account_list_section, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = savedAccountData[position]) {
            is String -> {
                val sectionHolder = holder as SectionViewHolder
                sectionHolder.sectionText.text = item
            }
            is SavedAccountGetDataDomain -> {
                val accountHolder = holder as AccountInfoViewHolder
                accountHolder.accountName.text = item.name
                accountHolder.accountNo.text = item.accountNumber
                accountHolder.bankName.text = "Lumi Bank" // Atau gunakan data yang sesuai

                accountHolder.itemView.setOnClickListener {
                    sharedPreferences.edit().apply {
                        putString("accountDestinationName", item.name)
                        putString("accountDestinationNo", item.accountNumber)
                        apply()
                    }
                    accountHolder.itemView.findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
                }
            }
        }
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (getItemViewType(position) == 1) {
//            val item = savedAccountData[position] as SavedAccountGetDataDomain
//            val accountHolder = holder as AccountInfoViewHolder
//            accountHolder.accountName.text = item.name
//            accountHolder.accountNo.text = item.accountNumber
//
//            accountHolder.itemView.setOnClickListener {
//                sharedPreferences.edit().apply {
//                    putString("accountDestinationName", item.name)
//                    putString("accountDestinationNo", item.accountNumber)
//                    apply()
//                }
//                accountHolder.itemView.findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
//            }
//        } else {
//            val sectionTitle = savedAccountData[position] as String
//            val sectionHolder = holder as SectionViewHolder
//            sectionHolder.sectionText.text = sectionTitle
//        }
//    }

    override fun getItemCount(): Int = savedAccountData.size

    inner class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImage: ImageView = itemView.findViewById(R.id.avatarImage)
        val accountName: TextView = itemView.findViewById(R.id.accountName)
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val accountNo: TextView = itemView.findViewById(R.id.accountNo)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionText: TextView = itemView.findViewById(R.id.sectionText)
    }
}