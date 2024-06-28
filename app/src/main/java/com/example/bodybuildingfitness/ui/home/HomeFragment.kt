package com.example.bodybuildingfitness.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var spinner: Spinner
    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    private val binding get() = _binding!!

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       val view = inflater.inflate(R.layout.fragment_home, container, false)

       spinner = view.findViewById(R.id.spinner)
       timerText = view.findViewById(R.id.timer_text)
       startButton = view.findViewById(R.id.start_button)
       stopButton = view.findViewById(R.id.stop_button)
       resetButton = view.findViewById(R.id.reset_button)

       val times = resources.getStringArray(R.array.timer_values)
       val timeValues = arrayOf(30000L, 60000L, 90000L,120000L,150000L,180000L,210000L,240000L,)

       var selectedTime = timeValues[0]
       spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
           override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               selectedTime = timeValues[position]
               resetTimer(selectedTime)
           }

           override fun onNothingSelected(parent: AdapterView<*>) {}
       }

       startButton.setOnClickListener {
           startTimer()
       }

       stopButton.setOnClickListener {
           stopTimer()
       }

       resetButton.setOnClickListener {
           resetTimer(selectedTime)
       }

       return view
   }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateTimerText()
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
    }

    private fun resetTimer(startTimeInMillis: Long) {
        stopTimer()
        timeLeftInMillis = startTimeInMillis
        updateTimerText()
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }
}

