package com.meldcx.webcapture.di

import android.app.Application
import androidx.room.Room
import com.meldcx.webcapture.data.LocalDao
import com.meldcx.webcapture.data.ScreenshotDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App level Module for
 * injecting instances
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDealDatabase(application: Application): ScreenshotDatabase {
        return Room.databaseBuilder(
                application,
                ScreenshotDatabase::class.java,
                "screenshot.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDealDao(screenshotDatabase: ScreenshotDatabase): LocalDao {
        return screenshotDatabase.localDao()
    }
}