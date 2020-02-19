package com.meldcx.webcapture.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.meldcx.webcapture.R
import com.meldcx.webcapture.data.model.Screenshot
import com.meldcx.webcapture.utils.Constant.URL_PARAMETER
import com.meldcx.webcapture.utils.afterTextChanged
import com.meldcx.webcapture.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import javax.inject.Inject

/**
 * Second Activity to show the list of screenshot information
 */

class SecondActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var viewModel: SecondActivityViewModel
    private val screenshotAdapter = ScreenshotAdapter(object : ScreenshotSelectionListener {
        override fun onDelete(screenshot: Screenshot) {
            viewModel.deleteScreenShots(screenshot)
        }

        override fun onSelection(screenshot: Screenshot) {
            val intent =  Intent()
            intent.putExtra(URL_PARAMETER, screenshot.url)
            setResult(RESULT_OK, intent)
            finish()
        }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting the view
        setContentView(R.layout.activity_second)

        //instance of viewmodel
        viewModel = ViewModelProviders.of(this, providerFactory).get(SecondActivityViewModel::class.java)

        //view extension for edittext view
        // watching text changes
        urlEditText.afterTextChanged {
            viewModel.setSearchQuery(it)
        }

        //initialising recyclerview
        initializeList()

        //subscribe for the data as Livedata
        subscribeUI()

        //initially the search box is empty
        viewModel.setSearchQuery("")
    }


    /**
     * getting the list of screenshots from viewmodel
     * and setting the data to adapter
     */
    private fun subscribeUI() {
        viewModel.screenShots.observe(this, Observer {
            screenshotAdapter.setData(it)
            screenshotAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this@SecondActivity, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this@SecondActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * initialising the recyclerview
     */
    private fun initializeList() {
        val layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager
        list.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        list.adapter = screenshotAdapter
    }
}