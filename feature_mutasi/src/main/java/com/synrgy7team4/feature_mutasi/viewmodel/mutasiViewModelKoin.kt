package com.synrgy7team4.feature_mutasi.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mutasiViewModelKoin = module {
    viewModel { MutasiViewmodel(get(), get(), get()) }
}


