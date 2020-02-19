package com.meldcx.webcapture.ui

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meldcx.webcapture.R
import com.meldcx.webcapture.data.model.Screenshot
import com.meldcx.webcapture.utils.Utils
import kotlinx.android.synthetic.main.screenshot_item.view.*


/**
 * RecyclerView adapter to show the screenshots
 */

class ScreenshotAdapter(private val screenshotSelectionListener: ScreenshotSelectionListener) :
        RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder>() {
    private val screenshots = mutableListOf<Screenshot>()

    /**
     * ViewHolder
     */
    inner class ScreenshotViewHolder(itemView: View, private val screenshotSelectionListener: ScreenshotSelectionListener) : RecyclerView.ViewHolder(itemView) {
        private val titleText = itemView.textView
        private val timeText = itemView.time
        private val imageView = itemView.imageView
        private val deleteButton = itemView.deleteButton

        fun bindPost(screenshot: Screenshot) {
            with(screenshot) {
                titleText.text = url
                timeText.text = Utils.getTime(timeStamp)
                imageView.setImageBitmap(BitmapFactory.decodeFile(imageLocation))
                deleteButton.setOnClickListener {
                    screenshotSelectionListener.onDelete(screenshot)
                }
                itemView.setOnClickListener {
                    screenshotSelectionListener.onSelection(screenshot)
                }
            }
        }
    }


    /**
     * set the list of screenshots
     */
    fun setData(values: List<Screenshot>) {
        screenshots.clear()
        screenshots.addAll(values)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ScreenshotViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.screenshot_item, parent, false)

        return ScreenshotViewHolder(textView, screenshotSelectionListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        holder.bindPost(screenshots[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = screenshots.size
}

interface ScreenshotSelectionListener {
    fun onDelete(screenshot: Screenshot)
    fun onSelection(screenshot: Screenshot)
}