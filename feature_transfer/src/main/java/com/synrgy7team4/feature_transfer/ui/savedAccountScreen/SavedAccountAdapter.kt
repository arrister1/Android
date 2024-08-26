package com.synrgy7team4.feature_transfer.ui.savedAccountScreen

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        if (getItemViewType(position) == 1) {
            val item = savedAccountData[position] as SavedAccountGetDataDomain
            val accountHolder = holder as AccountInfoViewHolder
            accountHolder.accountName.text = item.name
            accountHolder.accountNo.text = item.accountNumber

            accountHolder.itemView.setOnClickListener {
                sharedPreferences.edit().apply {
                    putString("accountDestinationName", item.name)
                    putString("accountDestinationNo", item.accountNumber)
                    apply()
                }
                accountHolder.itemView.findNavController().navigate(R.id.action_savedAccountFragment_to_transferInputFragment)
            }
        } else {
            val sectionTitle = savedAccountData[position] as String
            val sectionHolder = holder as SectionViewHolder
            sectionHolder.sectionText.text = sectionTitle
        }
    }

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