package com.synrgy7team4.feature_mutasi.ui.mutasiScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationDataDomain
import com.synrgy7team4.feature_mutasi.R
import java.text.SimpleDateFormat
import java.util.Locale

class MutationPerDateAdapter(
    //private var mutationList: List<MutationDataDomain>,
    private val groupedMutationList: Map<String, List<MutationDataDomain>>,
    private val accountNumber: String
) : RecyclerView.Adapter<MutationPerDateAdapter.ViewHolder>() {
    private val dates = groupedMutationList.keys.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_per_date, parent, false)
        )

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
//        holder.bind(mutationList[position])

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, groupedMutationList[date] ?: emptyList())
    }

   // override fun getItemCount(): Int = mutationList.size
   override fun getItemCount(): Int = groupedMutationList.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
        private val rvTransaksi: RecyclerView = itemView.findViewById(R.id.rv_per_transaction)

        fun bind(date: String, mutations: List<MutationDataDomain>) {
            tvTanggal.text = date
            val transferAdapter = MutationPerTransactionAdapter(mutations, accountNumber)
            rvTransaksi.adapter = transferAdapter
            rvTransaksi.layoutManager = LinearLayoutManager(rvTransaksi.context)
        }

//        fun bind(mutation: MutationDataDomain) {
//            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
//
//            val date = inputFormat.parse(mutation.datetime)
//            tvTanggal.text = outputFormat.format(date)
//

//
//            val transferAdapter = MutationPerTransactionAdapter(mutationList, accountNumber)
//            rvTransaksi.adapter = transferAdapter
//            rvTransaksi.layoutManager = LinearLayoutManager(rvTransaksi.context)
//        }
    }
}