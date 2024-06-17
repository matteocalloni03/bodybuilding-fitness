package com.example.bodybuildingfitness.ui.gallery

import android.content.ContentValues.TAG
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentGalleryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private lateinit var auth: FirebaseAuth

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

        // Inizializza FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val editTextEvento = view.findViewById<EditText>(R.id.editTextEvento)
        val buttonAggiungi1 = binding.btnAggiungi

        val oggi = Calendar.getInstance()
        calendarView.date = oggi.timeInMillis

        buttonAggiungi1.setOnClickListener {
            val testoEvento = editTextEvento.text.toString()
            val dataSelezionata = calendarView.date

            if (testoEvento.isNotBlank()) {
                val db = FirebaseFirestore.getInstance()
                val user = auth.currentUser

                if (user != null) {
                    val dataFormattata = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dataSelezionata)
                    val eventoData = hashMapOf(
                        "userId" to user.email,
                        "testo" to testoEvento,
                        "data" to dataFormattata
                    )

                    db.collection("eventi")
                        .add(eventoData)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "Evento aggiunto con ID: ${documentReference.id}")
                            Toast.makeText(requireContext(), "Evento aggiunto!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Errore nell'aggiunta dell'evento", e)
                        }

                    editTextEvento.text.clear()
                } else {
                    Log.w(TAG, "Utente non autenticato")
                    Toast.makeText(requireContext(), "Utente non autenticato", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Inserisci il testo dell'evento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
