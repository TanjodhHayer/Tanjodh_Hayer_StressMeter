package com.example.tanjodh_hayer_stressmeter.ui.home

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context) : BaseAdapter() {

    private val imageResourceIds = IntArray(NUM_IMAGES)
    private var currentPage = 1

    init {
        updateImageResourceIds()
    }
    fun setCurrentPage(page: Int) {
        currentPage = page
        updateImageResourceIds()
    }

    private fun updateImageResourceIds() {
        val startImageIndex = (currentPage - 1) * 16
        for (i in 0 until NUM_IMAGES) {
            val resourcePath = "img${startImageIndex + i + 1}"
            val resourceId = context.resources.getIdentifier(resourcePath, "drawable", context.packageName)
            Log.d("ImageAdapter", "Resource Path: $resourcePath, Resource ID: $resourceId")
            imageResourceIds[i] = resourceId
        }
    }

    override fun getCount(): Int {
        return imageResourceIds.size
    }

    override fun getItem(position: Int): Any {
        return imageResourceIds[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        imageView.setImageResource(imageResourceIds[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = AbsListView.LayoutParams(300, 300)

        return imageView
    }
    companion object {
        const val NUM_IMAGES = 16
    }
}
