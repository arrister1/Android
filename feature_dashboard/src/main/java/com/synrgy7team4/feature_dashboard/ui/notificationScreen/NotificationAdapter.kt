package com.synrgy7team4.feature_dashboard.ui.notificationScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy7team4.domain.feature_dashboard.model.response.NotificationGetResponseItemDomain
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.NotificationItemListBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationAdapter(
    private val notificationList: List<NotificationGetResponseItemDomain>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item_list, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notificationList[position])

    override fun getItemCount(): Int = notificationList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NotificationItemListBinding.bind(itemView)

        fun bind(item: NotificationGetResponseItemDomain) {
            binding.tvJudulTransaksi.text = item.title
            binding.tvDeskripsiTransaksi.text = item.body
            binding.tvWaktuTransaksi.text = convertDateFormat(item.sentAt)
        }
    }

    private fun convertDateFormat(dateString: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.US)
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale("in", "ID"))

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}