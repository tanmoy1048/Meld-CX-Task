package com.meldcx.webcapture.di.main

import androidx.lifecycle.ViewModel
import com.meldcx.webcapture.di.ViewModelKey
import com.meldcx.webcapture.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dependency for injecting viewmodel factory
 * into the MainViewModel
 */

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}