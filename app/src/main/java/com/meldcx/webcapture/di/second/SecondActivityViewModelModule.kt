package com.meldcx.webcapture.di.second

import androidx.lifecycle.ViewModel
import com.meldcx.webcapture.di.ViewModelKey
import com.meldcx.webcapture.ui.SecondActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dependency for injecting viewmodel factory
 * into the SecondViewModel
 */

@Module
abstract class SecondActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SecondActivityViewModel::class)
    abstract fun bindSecondActivityViewModel(viewModel: SecondActivityViewModel): ViewModel
}