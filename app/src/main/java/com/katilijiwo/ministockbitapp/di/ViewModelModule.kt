package com.katilijiwo.ministockbitapp.di

import com.katilijiwo.ministockbitapp.ui.login.LoginViewModel
import com.katilijiwo.ministockbitapp.ui.watchlist.WatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WatchListViewModel(get())
    }
    viewModel {
        LoginViewModel()
    }
}