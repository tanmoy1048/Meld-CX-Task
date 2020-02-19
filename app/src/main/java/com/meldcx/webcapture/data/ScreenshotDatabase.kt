package com.meldcx.webcapture.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meldcx.webcapture.data.model.Screenshot


/**
 * Room Database to store screenshot data
 */

@Database(
        entities = [Screenshot::class],
        version = 1
)
abstract class ScreenshotDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao
}