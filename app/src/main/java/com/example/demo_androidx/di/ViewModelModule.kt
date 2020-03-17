package com.example.demo_androidx.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo_androidx.di.ViewModelFactory
import com.example.demo_androidx.di.ViewModelKey
import com.example.demo_androidx.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindUserViewModel(viewModel: MainViewModel): ViewModel

}