package com.synrgy7team4.feature_transfer.domain

import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.TransferReq


fun TransferReq.toEntity(): TransferRequest {
    return TransferRequest(
        accountFrom = this.accountFrom,
        accountTo = this.accountTo,
        amount = this.amount,
        description = this.description,
        pin = this.pin
    )
}

fun AccountReq.toEntity(): AccountRequest {
    return AccountRequest(
        additionalProp1 = this.additionalProp1,
        additionalProp2 = this.additionalProp2,
        additionalProp3 = this.additionalProp3
    )
}

fun BalanceReq.toEntity(): BalanceRequest {
    return BalanceRequest(
        accountNumber = this.accountNumber,
        newBalance = this.newBalance
    )
}