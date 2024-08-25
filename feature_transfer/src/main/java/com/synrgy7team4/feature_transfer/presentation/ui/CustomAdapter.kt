package com.synrgy7team4.feature_transfer.presentation.ui.transfer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.feature_transfer.R
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData


class CustomAdapter(private val context: Context, private var mList: MutableList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var filteredList: MutableList<Any> = mList.toMutableList()

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""

                val newList = if (query.isEmpty()) {
                    mList
                } else {
                    val tempList = mutableListOf<Any>()
                    var currentSection: String? = null
                    var sectionHasMatches = false

                    for (item in mList) {
                        if (item is String) {
                            /*// This is a section header
                            if (sectionHasMatches) {
                                // Add the previous section header if it had matches
                                tempList.add(currentSection!!)
                            }*/
                            currentSection = item
                            sectionHasMatches = false
                        } else if (item is AccountData) {
                            if (item.name.lowercase().contains(query) ||
                                item.accountNumber.contains(query) ||
                                item.id.contains(query)) {
                                if (!sectionHasMatches) {
                                    // Add the section header if this is the first match
                                    tempList.add(currentSection!!)
                                    sectionHasMatches = true
                                }
                                tempList.add(item)
                            }
                        }
                    }
                    tempList
                }

                val filterResults = FilterResults()
                filterResults.values = newList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Any>
                notifyDataSetChanged()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            AccountInfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.account_info_card, parent, false))
        } else {
            SectionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.account_list_section, parent, false))
        }
    }

    fun updateList(newList: List<Any>) {
        mList = newList.toMutableList()
        filteredList = mList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val item = filteredList[position] as AccountData
            val accountHolder = holder as AccountInfoViewHolder
            accountHolder.accountName.text = item.name
            accountHolder.accountName.contentDescription = item.name
            //accountHolder.bankName.text = item.id
            accountHolder.accountNo.text = item.accountNumber
            accountHolder.accountNo.contentDescription = item.accountNumber
            accountHolder.bankName.text = "Lumi Bank"
            accountHolder.bankName.contentDescription = "Lumi Bank"

            val bankName = accountHolder.bankName.text.toString()

            accountHolder.itemView.setOnClickListener {
                onClickListener?.onClick(position, item, bankName)
            }
        } else {
            val sectionTitle = filteredList[position] as String
            val sectionHolder = holder as SectionViewHolder
            sectionHolder.sectionText.text = sectionTitle
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (filteredList[position] is String) 0 else 1
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: AccountData, bank:String)
    }

    class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImage: ImageView = itemView.findViewById(R.id.avatarImage)
        val accountName: TextView = itemView.findViewById(R.id.accountName)
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val accountNo: TextView = itemView.findViewById(R.id.accountNo)
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionText: TextView = itemView.findViewById(R.id.sectionText)
    }
}

//class CustomAdapter(
//    val context: Context,
//    private var mList: MutableList<Any>
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private var onClickListener: OnClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == 1) {
//            AccountInfoViewHolder(
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.account_info_card, parent, false)
//            )
//        } else {
//            SectionViewHolder(
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.account_list_section, parent, false)
//            )
//        }
//    }
//
//    fun filterList(filterlist: MutableList<Any>) {
//        mList = filterlist
//        notifyDataSetChanged()
//    }
//
//    override fun onBindViewHolder(anyHolder: RecyclerView.ViewHolder, position: Int) {
//        if (getItemViewType(position) == 1) {
//            val itemsViewModel = mList[position] as AccountData
//            val holder = anyHolder as AccountInfoViewHolder
//            holder.accountName.text = itemsViewModel.name
//            holder.bankName.text = "Bank XYZ" // Ganti dengan informasi bank yang sesuai jika ada
//            holder.accountNo.text = itemsViewModel.accountNumber
//
//            holder.itemView.setOnClickListener {
//                onClickListener?.onClick(position, itemsViewModel)
//            }
//        } else {
//            val itemsViewModel = mList[position] as String
//            val holder = anyHolder as SectionViewHolder
//            holder.sectionText.text = itemsViewModel
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (mList[position]) {
//            is String -> 0
//            else -> 1
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    fun setOnClickListener(listener: OnClickListener?) {
//        this.onClickListener = listener
//    }
//
//    interface OnClickListener {
//        fun onClick(position: Int, model: AccountData)
//    }
//
//    class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val avatarImage: ImageView = itemView.findViewById(R.id.avatarImage)
//        val accountName: TextView = itemView.findViewById(R.id.accountName)
//        val bankName: TextView = itemView.findViewById(R.id.bankName)
//        val accountNo: TextView = itemView.findViewById(R.id.accountNo)
//    }
//
//    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val sectionText: TextView = itemView.findViewById(R.id.sectionText)
//    }
//}
//class CustomAdapter(val context: Context, private var mList: MutableList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private var onClickListener: OnClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == 1) {
//            AccountInfoViewHolder(LayoutInflater.from(parent.context)
//                .inflate(R.layout.account_info_card, parent, false))
//        }else{
//            SectionViewHolder(LayoutInflater.from(parent.context)
//                .inflate(R.layout.account_list_section, parent, false))
//        }
//    }
//
//    // method for filtering our recyclerview items.
//    fun filterList(filterlist: MutableList<Any>) {
//        // below line is to add our filtered
//        // list in our course array list.
//        mList = filterlist
//        // below line is to notify our adapter
//        // as change in recycler view data.
//        notifyDataSetChanged()
//    }
//
//    override fun onBindViewHolder(anyHolder: RecyclerView.ViewHolder, position: Int) {
//
//        if (getItemViewType(position) == 1) {
//            val itemsViewModel = mList[position] as SavedAccountDataObject
//            val holder = anyHolder as AccountInfoViewHolder
//            //        holder.avatarImage.setImageResource(ItemsViewModel.image)
//            holder.accountName.text = itemsViewModel.accountName
//            holder.bankName.text = itemsViewModel.bankName
//            holder.accountNo.text = itemsViewModel.accountNo
//
//            // Set click listener for the item view
//            holder.itemView.setOnClickListener {
//                onClickListener?.onClick(position, itemsViewModel)
//            }
//        }else{
//            val itemsViewModel = mList[position] as String
//            val holder = anyHolder as SectionViewHolder
//            holder.sectionText.text = itemsViewModel
//        }
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (mList[position]) {
//            is String -> 0
//            else -> 1
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    // Set the click listener for the adapter
//    fun setOnClickListener(listener: OnClickListener?) {
//        this.onClickListener = listener
//    }
//
//    // Interface for the click listener
//    interface OnClickListener {
//        fun onClick(position: Int, model: SavedAccountDataObject)
//    }
//
//    // Holds the views for adding it to image and text
//    class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val avatarImage = itemView.findViewById<ImageView>(R.id.avatarImage)
//        val accountName = itemView.findViewById<TextView>(R.id.accountName)
//        val bankName = itemView.findViewById<TextView>(R.id.bankName)
//        val accountNo = itemView.findViewById<TextView>(R.id.accountNo)
//    }
//
//    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val sectionText = itemView.findViewById<TextView>(R.id.sectionText)
//    }
//}