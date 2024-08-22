package com.synrgy7team4.feature_transfer.data.remote.response.transschedule

import com.google.gson.annotations.SerializedName

data class TransSchedData(

    @field:SerializedName("accountTo")
    val accountTo: String? = null,

    @field:SerializedName("amount")
    val amount: Int? = null,

    @field:SerializedName("datetime")
    val datetime: String? = null,

    @field:SerializedName("balance")
    val balance: Int? = null,

    @field:SerializedName("nameAccountFrom")
    val nameAccountFrom: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("accountFrom")
    val accountFrom: String? = null,

    @field:SerializedName("nameAccountTo")
    val nameAccountTo: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)