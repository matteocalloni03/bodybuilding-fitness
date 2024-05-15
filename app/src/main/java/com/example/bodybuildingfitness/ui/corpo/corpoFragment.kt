package com.example.bodybuildingfitness.ui.corpo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.databinding.FragmentCorpoBinding


class corpoFragment : Fragment() {

    private var _binding: FragmentCorpoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val CorpoViewModel= ViewModelProvider(this).get(CorpoViewModel::class.java)

        _binding = FragmentCorpoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCorpo

        CorpoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}