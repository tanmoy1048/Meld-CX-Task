package com.meldcx.webcapture.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meldcx.webcapture.data.LocalDao
import com.meldcx.webcapture.data.model.Screenshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class SecondActivityViewModel @Inject constructor(val localDao: LocalDao) : ViewModel() {
    companion object {
        private const val TAG = "SecondActivityViewModel"
    }

    val errorMessage = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()

    //searchQuery live data to observe
    private val searchQuery = MutableLiveData<String>()
    val screenShots = Transformations.switchMap(searchQuery) {
        localDao.getScreenshot(it)
    }

    /**
     * Deleting screenshots
     */
    fun deleteScreenShots(screenShot:Screenshot){
        viewModelScope.launch {
            try {
                //deleting file
                val file = File(screenShot.imageLocation)
                file.delete()
                //deleting room entry
                localDao.deleteScreenshot(screenShot)
            }catch (e:Exception){
                errorMessage.postValue(e.message)
            }

            withContext(Dispatchers.Main) {
                //letting the UI know the success
                successMessage.postValue("Deleted")
            }
        }
    }

    //changes of text
    fun setSearchQuery(query: String) {
        searchQuery.postValue(query)
    }
}