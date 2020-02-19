package com.meldcx.webcapture.ui

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meldcx.webcapture.data.LocalDao
import com.meldcx.webcapture.data.model.Screenshot
import com.meldcx.webcapture.utils.Constant.IMAGE_DIR
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(val localDao: LocalDao, val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "MainActivityViewModel"
    }

    val errorMessage = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()

    /**
     * writing the bitmap into internal storage
     * and return the absolute path of the file
     */
    private fun saveBitmap(bitmap: Bitmap, fileName: String): String {
        val cw = ContextWrapper(application)
        val directory = cw.getDir(IMAGE_DIR, MODE_PRIVATE)
        // Create imageDir if not available
        val file = File(directory, fileName)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: Exception) {
            errorMessage.postValue(e.message)
            e.printStackTrace()
        } finally {
            try {
                //closing the file
                fos!!.close()
            } catch (e: IOException) {
                errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
        //returning the file path
        return file.absolutePath
    }

    /**
     * saving the bitmap
     * and writing into the Room database
     */
    fun saveScreenshot(bitmap: Bitmap, url: String) {
        viewModelScope.launch {
            withContext(IO) {
                //timestamp to save the time
                val currentTimestamp = System.currentTimeMillis()
                //inserting the data into Room database
                //filename as timestamp.jpg
                localDao.insertScreenshot(Screenshot(url, saveBitmap(bitmap, "$currentTimestamp.jpg"), currentTimestamp))
            }

            withContext(Main) {
                //letting the UI know the success
                successMessage.postValue("Everything is saved")
            }
        }

    }
}