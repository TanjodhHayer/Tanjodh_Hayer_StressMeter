package com.example.tanjodh_hayer_stressmeter.ui.home

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.tanjodh_hayer_stressmeter.R

class HomeFragment : Fragment() {


    private var currentPage = 1
    private val pages = 3
    private var selectedImagePosition = -1
    private var playSound = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val gridView = view.findViewById<GridView>(R.id.gridView)
        val btnMoreImages = view.findViewById<Button>(R.id.btnMoreImages)


        val imgAdapter = ImageAdapter(requireContext())
        gridView.adapter = imgAdapter


        gridView.setOnItemClickListener { parent, view, position, id ->
            selectedImagePosition = position
            val imgID = imgAdapter.getItem(position) as Int

            val intent = Intent(requireContext(), LargerImageActivity::class.java)
            intent.putExtra("imageResourceId", imgID)
            intent.putExtra("selectedImagePosition", selectedImagePosition)
            startActivity(intent)
            onPause()
        }



        btnMoreImages.setOnClickListener {
            currentPage++
            if (currentPage > pages) {
                currentPage = 1
            }
            imgAdapter.setCurrentPage(currentPage)
            imgAdapter.notifyDataSetChanged()

            onPause()
        }


        return view
    }

    private val sHandler = android.os.Handler()
    private val soundRunnable = object : Runnable {
        override fun run() {
            if (playSound){
                val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sound)
                mediaPlayer.start()

                sHandler.postDelayed(this, 3000)
            }

        }
    }

    private val vHandler = android.os.Handler()
    private val vibrationRunnable = object : Runnable {
        override fun run() {
            if (playSound){
                val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(2000)
                }
                Log.d("Vibration", "Device vibrated")
                vHandler.postDelayed(this, 3000)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        sHandler.post(soundRunnable)
        vHandler.post(vibrationRunnable)
    }

    override fun onPause() {
        super.onPause()
        sHandler.removeCallbacks(soundRunnable)
        vHandler.removeCallbacks(vibrationRunnable)
        playSound = false
    }






}
