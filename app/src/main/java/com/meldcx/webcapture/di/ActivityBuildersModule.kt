package com.meldcx.webcapture.di

import com.meldcx.webcapture.di.second.SecondActivityViewModelModule
import com.meldcx.webcapture.di.main.MainViewModelsModule
import com.meldcx.webcapture.ui.SecondActivity
import com.meldcx.webcapture.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Adding up modules for the activities
 */

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SecondActivityViewModelModule::class])
    abstract fun contributeAnotherActivity(): SecondActivity
}