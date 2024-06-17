package com.example.bodybuildingfitness.ui.gallery

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentGalleryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!
    private var dataSelezionata: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val editTextEvento = view.findViewById<EditText>(R.id.editTextEvento)
        val textViewEvento = view.findViewById<TextView>(R.id.textevento)
        val buttonAggiungi1 = binding.btnAggiungi

        // Mostra gli eventi per la data di oggi inizialmente
        val oggi = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        dataSelezionata = sdf.format(oggi.time)
        mostraEvento(dataSelezionata, textViewEvento)

        buttonAggiungi1.setOnClickListener {
            val testoEvento = editTextEvento.text.toString()

            if (testoEvento.isNotBlank()) {
                val db = FirebaseFirestore.getInstance()
                val user = auth.currentUser

                if (user != null) {
                    val eventoData = hashMapOf(
                        "userId" to user.email,
                        "testo" to testoEvento,
                        "data" to dataSelezionata
                    )

                    db.collection("eventi")
                        .add(eventoData)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "Evento aggiunto con ID: ${documentReference.id}")
                            Toast.makeText(requireContext(), "Evento aggiunto!", Toast.LENGTH_SHORT).show()
                            // Aggiorna gli eventi visualizzati dopo aver aggiunto un nuovo evento
                            mostraEvento(dataSelezionata, textViewEvento)
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

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            dataSelezionata = sdf.format(calendar.time)
            mostraEvento(dataSelezionata, textViewEvento)
        }
    }

    private fun mostraEvento(data: String, textView: TextView) {
        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val user = auth.currentUser

        if (user != null) {
            db.collection("eventi")
                .whereEqualTo("userId", user.email)
                .whereEqualTo("data", data)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val eventi = StringBuilder()
                        for (document in documents) {
                            val evento = document.getString("testo")
                            eventi.append("- ").append(evento).append("\n")
                        }
                        textView.text = eventi.toString().trim()  // Rimuovi l'ultimo newline
                    } else {
                        textView.text = "Nessun evento trovato"
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Errore nel recupero dell'evento", e)
                    textView.text = "Errore nel recupero dell'evento"
                }
        } else {
            Log.w(TAG, "Utente non autenticato")
            textView.text = "Utente non autenticato"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}










