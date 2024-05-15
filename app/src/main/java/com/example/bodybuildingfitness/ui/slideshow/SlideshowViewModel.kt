package com.example.bodybuildingfitness.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "scegli la scheda che desideri in base ai giorni di allenamento che vuoi fare"
    }
    val text: LiveData<String> = _text
}