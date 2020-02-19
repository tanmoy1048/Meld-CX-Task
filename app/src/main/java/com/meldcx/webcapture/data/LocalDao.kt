package com.meldcx.webcapture.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.meldcx.webcapture.data.model.Screenshot


/**
 * Database queries to manipulate screenshotData
 */

@Dao
interface LocalDao {
    @Query("SELECT * FROM Screenshot WHERE url LIKE '%' ||:queryString|| '%'")
    fun getScreenshot(queryString: String): LiveData<List<Screenshot>>

    @Insert
    suspend fun insertScreenshot(screenshot: Screenshot)

    @Delete
    suspend fun deleteScreenshot(screenshot: Screenshot)

    @Query("DELETE FROM Screenshot")
    suspend fun deleteScreenshot()
}