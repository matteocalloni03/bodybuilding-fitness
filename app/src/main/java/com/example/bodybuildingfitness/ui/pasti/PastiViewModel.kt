package com.example.bodybuildingfitness.ui.pasti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PastiViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is pasti Fragment"
    }
    val text: LiveData<String> = _text
}