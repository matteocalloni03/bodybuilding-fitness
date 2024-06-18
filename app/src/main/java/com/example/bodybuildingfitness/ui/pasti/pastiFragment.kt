package com.example.bodybuildingfitness.ui.pasti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.databinding.FragmentCorpoBinding
import com.example.bodybuildingfitness.databinding.FragmentPastiBinding
import com.example.bodybuildingfitness.ui.corpo.CorpoViewModel

class pastiFragment : Fragment() {

    private var _binding: FragmentPastiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val PastiViewModel= ViewModelProvider(this).get(PastiViewModel::class.java)

        _binding = FragmentPastiBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}