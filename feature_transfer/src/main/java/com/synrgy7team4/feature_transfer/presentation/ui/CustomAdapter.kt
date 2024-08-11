package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.feature_transfer.R

class CustomAdapter(val context: Context, private var mList: MutableList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            AccountInfoViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.account_info_card, parent, false))
        }else{
            SectionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.account_list_section, parent, false))
        }
    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: MutableList<Any>) {
        // below line is to add our filtered
        // list in our course array list.
        mList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(anyHolder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1) {
            val itemsViewModel = mList[position] as SavedAccountDataObject
            val holder = anyHolder as AccountInfoViewHolder
            //        holder.avatarImage.setImageResource(ItemsViewModel.image)
            holder.accountName.text = itemsViewModel.accountName
            holder.bankName.text = itemsViewModel.bankName
            holder.accountNo.text = itemsViewModel.accountNo

            // Set click listener for the item view
            holder.itemView.setOnClickListener {
                onClickListener?.onClick(position, itemsViewModel)
            }
        }else{
            val itemsViewModel = mList[position] as String
            val holder = anyHolder as SectionViewHolder
            holder.sectionText.text = itemsViewModel
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (mList[position]) {
            is String -> 0
            else -> 1
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: SavedAccountDataObject)
    }

    // Holds the views for adding it to image and text
    class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImage = itemView.findViewById<ImageView>(R.id.avatarImage)
        val accountName = itemView.findViewById<TextView>(R.id.accountName)
        val bankName = itemView.findViewById<TextView>(R.id.bankName)
        val accountNo = itemView.findViewById<TextView>(R.id.accountNo)
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionText = itemView.findViewById<TextView>(R.id.sectionText)
    }
}