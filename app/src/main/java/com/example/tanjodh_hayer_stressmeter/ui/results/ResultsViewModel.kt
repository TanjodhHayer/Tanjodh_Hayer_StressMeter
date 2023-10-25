package com.example.tanjodh_hayer_stressmeter.ui.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is results Fragment"
    }
    val text: LiveData<String> = _text
}