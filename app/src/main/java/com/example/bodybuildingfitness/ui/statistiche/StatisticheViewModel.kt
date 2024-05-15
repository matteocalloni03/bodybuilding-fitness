package com.example.bodybuildingfitness.ui.statistiche

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticheViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is statistiche Fragment"
    }
    val text: LiveData<String> = _text
}