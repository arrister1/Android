package com.synrgy7team4.domain.feature_dashboard.model.response

data class NotificationGetResponseItemDomain(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    val sentAt: String? = null,
    val read: Boolean
)