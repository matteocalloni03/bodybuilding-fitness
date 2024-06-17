package com.example.bodybuildingfitness.ui.gallery

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val editTextEvento = view.findViewById<EditText>(R.id.editTextEvento)
        val buttonAggiungi = view.findViewById<Button>(R.id.buttonAggiungi)

        // Imposta la data di oggi
        val oggi = Calendar.getInstance()
        calendarView.date = oggi.timeInMillis

        buttonAggiungi.setOnClickListener {
            val testoEvento = editTextEvento.text.toString()
            val dataSelezionata = calendarView.date // Ottieni la data selezionata in millisecondi

            if (testoEvento.isNotBlank()) {
                aggiungiEventoAlCalendario(testoEvento, dataSelezionata)
                Toast.makeText(requireContext(), "Evento aggiunto!", Toast.LENGTH_SHORT).show()
                editTextEvento.text.clear() // Pulisci l'EditText
            } else {
                Toast.makeText(requireContext(), "Inserisci il testo dell'evento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun aggiungiEventoAlCalendario(testoEvento: String, dataSelezionata: Long) {
        // Implementa qui la logica per aggiungere l'evento al calendario
        // Puoi usare il Calendar Provider di Android o una libreria di terze parti
        // Ad esempio, usando il Calendar Provider:
        val intent = Intent(Intent.ACTION_INSERT)}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}