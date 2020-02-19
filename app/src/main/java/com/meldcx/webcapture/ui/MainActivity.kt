package com.meldcx.webcapture.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.meldcx.webcapture.R
import com.meldcx.webcapture.utils.Constant.SECOND_ACTIVITY_REQUEST_CODE
import com.meldcx.webcapture.utils.Constant.URL_PARAMETER
import com.meldcx.webcapture.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/**
 * Activity to load an url into a webview
 * and take screenshot and save the data into the database
 */
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting the view
        setContentView(R.layout.activity_main)

        //instance of viewmodel
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainActivityViewModel::class.java)

        //webview client: to show loading indicator and error message
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.INVISIBLE
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Toast.makeText(this@MainActivity, "Cannot load page", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
        }

        //Go button click listener
        goButton.setOnClickListener {
            //hide keyboard
            hideKeyboard()
            //checking if there is any text @urlEditText
            if (!TextUtils.isEmpty(urlEditText.text.toString())) {
                //showing the progressbar
                progressBar.visibility = View.VISIBLE
                //loading the url
                webView.loadUrl(urlEditText.text.toString())
            } else {
                Toast.makeText(this@MainActivity, "Please enter a url", Toast.LENGTH_SHORT).show()
            }
        }

        //capture button click listener
        captureButton.setOnClickListener {
            //capture only if the progressbar is not visible meaning something is being loaded and urledittext is not empty
            if (progressBar.visibility == View.INVISIBLE && !TextUtils.isEmpty(urlEditText.text.toString())) {
                viewModel.saveScreenshot(getBitmapOfWebView(), urlEditText.text.toString())
            }

            if (progressBar.visibility == View.VISIBLE) {
                Toast.makeText(this@MainActivity, "an url is being loaded", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(urlEditText.text.toString())) {
                Toast.makeText(this@MainActivity, "please type an URL", Toast.LENGTH_SHORT).show()
            }
        }

        //history button click listener
        historyButton.setOnClickListener {
            //starting the second activity
            startActivityForResult(Intent(this, SecondActivity::class.java), SECOND_ACTIVITY_REQUEST_CODE)
        }

        subscribeUI()
    }

    /**
     * hide keyboard
     */
    private fun hideKeyboard(){
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /**
     * subscribing for any error message and success message
     */
    private fun subscribeUI() {
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * capturing the bitmap from the webview
     */
    private fun getBitmapOfWebView(): Bitmap {
        val bitmap = Bitmap.createBitmap(webView.width, webView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webView.draw(canvas)
        return bitmap
    }


    /**
     * getting the data from SecondActivity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val url = data?.getStringExtra(URL_PARAMETER)
                if (!TextUtils.isEmpty(url)) {
                    urlEditText.setText(url)
                    progressBar.visibility = View.VISIBLE
                    webView.loadUrl(url)
                }
            }
        }
    }
}