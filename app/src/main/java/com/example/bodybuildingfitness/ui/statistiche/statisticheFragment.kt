package com.example.bodybuildingfitness.ui.statistiche

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.databinding.FragmentCorpoBinding

import com.example.bodybuildingfitness.databinding.FragmentStatisticheBinding
import com.example.bodybuildingfitness.ui.corpo.CorpoViewModel

class statisticheFragment : Fragment() {

    private var _binding: FragmentStatisticheBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val StatisticheViewModel= ViewModelProvider(this).get(StatisticheViewModel::class.java)

        _binding = FragmentStatisticheBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStatistiche

        StatisticheViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}