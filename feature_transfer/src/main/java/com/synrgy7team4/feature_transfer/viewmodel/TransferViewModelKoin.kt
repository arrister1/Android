package com.synrgy7team4.feature_transfer.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transferViewModelKoin = module {
    viewModel { TransferViewModel(get(), get(), get()) }
}