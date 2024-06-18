package com.example.bodybuildingfitness.ui.corpo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
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


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val imageView1 = view.findViewById<ImageView>(R.id.imageuomo)
        val imageView2 = view.findViewById<ImageView>(R.id.imagedonna)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radiouomo -> {
                    imageView1.visibility = View.VISIBLE
                    imageView2.visibility = View.GONE
                }
                R.id.radiodonna -> {
                    imageView1.visibility = View.GONE
                    imageView2.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}