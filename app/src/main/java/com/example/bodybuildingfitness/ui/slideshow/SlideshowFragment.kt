package com.example.bodybuildingfitness.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.TextSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //giorno 1
        val button1 = binding.button1
        button1.setOnClickListener {
            val intent = Intent(requireContext(), giorni1::class.java)
            startActivity(intent)
        }
        //giorno 2
        val button2 = binding.button2
        button2.setOnClickListener {
            val intent = Intent(requireContext(), giorni2::class.java)
            startActivity(intent)
        }
        //giorno 3
        val button3 = binding.button3
        button3.setOnClickListener {
            val intent = Intent(requireContext(), giorni3::class.java)
            startActivity(intent)
        }
        //giorno 4
        val button4 = binding.button4
        button4.setOnClickListener {
            val intent = Intent(requireContext(), giorni4::class.java)
            startActivity(intent)
        }
        //giorno 5
        val button5 = binding.button5
        button5.setOnClickListener {
            val intent = Intent(requireContext(), giorni5::class.java)
            startActivity(intent)
        }

        //giorni personalizzata
        val button6 = binding.button6
        button6.setOnClickListener {
            val intent = Intent(requireContext(), giornipersonalizzata::class.java)
            startActivity(intent)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}