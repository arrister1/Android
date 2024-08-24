package com.synrgy7team4.feature_mutasi.ui.mutasiScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationDataDomain
import com.synrgy7team4.feature_mutasi.R

class MutationPerTransactionAdapter(
    private val mutationList: List<MutationDataDomain>,
    private val accountNumber: String
) : RecyclerView.Adapter<MutationPerTransactionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_transaction, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mutationList[position])

    override fun getItemCount(): Int = mutationList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icTransaksi: ImageView = itemView.findViewById(R.id.ic_transaksi)
        private val tvJenisTransaksi: TextView = itemView.findViewById(R.id.tv_tipe_transaksi)
        private val tvKeteranganTransaksi: TextView = itemView.findViewById(R.id.tv_keterangan_transaksi)
        private val tvNominalIdr: TextView = itemView.findViewById(R.id.tv_nominal_idr)

        fun bind(mutation: MutationDataDomain) {
            when (mutation.type) {
                "transfer" -> {
                    icTransaksi.setImageResource(com.synrgy7team4.common.R.drawable.ic_transfer)
                    tvJenisTransaksi.text = "Transfer"
                    tvKeteranganTransaksi.text = "Transfer ke ${mutation.accountTo}"
                }

                "top_up" -> {
                    icTransaksi.setImageResource(com.synrgy7team4.common.R.drawable.ic_topup)
                    tvJenisTransaksi.text = "Top Up"
                    tvKeteranganTransaksi.text = "Top Up ke ${mutation.accountTo}"
                }
            }
            if (mutation.accountFrom == accountNumber) {
                tvNominalIdr.text = "- IDR ${mutation.amount}"
                tvNominalIdr.setTextColor(ContextCompat.getColor(itemView.context, R.color.secondary_red))
            } else {
                tvNominalIdr.text = "+ IDR ${mutation.amount}"
                tvNominalIdr.setTextColor(ContextCompat.getColor(itemView.context, R.color.secondary_green))
            }
        }
    }
}