package com.example.tanjodh_hayer_stressmeter.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.example.tanjodh_hayer_stressmeter.R
import java.io.File
import java.io.FileWriter

class LargerImageActivity : Activity() {
    private var selectedImg = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.larger_image_layout)

        val largerImageView = findViewById<ImageView>(R.id.largerImageView)
        val closeButton = findViewById<Button>(R.id.closeButton)
        val submitButton = findViewById<Button>(R.id.submitButton)

        selectedImg = intent.getIntExtra("selectedImagePosition", -1)

        if (selectedImg == -1) {
            finish()
            return
        }
        val imageId = intent.getIntExtra("imageResourceId", 0)
        largerImageView.setImageResource(imageId)

        closeButton.setOnClickListener {
            finish()
        }

        submitButton.setOnClickListener {
            val stressScore = StressScoreUtil.getStressScoreForPosition(selectedImg)
            writeStressScoreToCSV(selectedImg)
            //Log.d("LargerImageActivity", "Stress Score: $stressScore")
            //finish()
            finishAffinity() // close whole app so line chart will be updated with new info
        }

    }

    private fun writeStressScoreToCSV(selectedImagePosition: Int) {
        val file = File(this.filesDir, "stress_timestamp.csv")

        if (!file.exists()) {
            file.createNewFile()
        }

        val stressScore = StressScoreUtil.getStressScoreForPosition(selectedImagePosition)
        val timestamp = System.currentTimeMillis()
        val csvRow = "$timestamp,$stressScore\n"

        try {
            val writer = FileWriter(file, true)
            writer.append(csvRow)
            writer.close()
            //Log.d("CSV Write", "Data written to CSV file: $csvRow")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CSV Write", "Error writing to CSV file: ${e.message}")
        }
    }

}
