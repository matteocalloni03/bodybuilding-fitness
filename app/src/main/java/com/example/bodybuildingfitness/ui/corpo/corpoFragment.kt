package com.example.bodybuildingfitness.ui.corpo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bodybuildingfitness.R
import com.example.bodybuildingfitness.databinding.FragmentCorpoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class corpoFragment : Fragment() {

    private var _binding: FragmentCorpoBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val CorpoViewModel = ViewModelProvider(this).get(CorpoViewModel::class.java)

        _binding = FragmentCorpoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val imageView1 = view.findViewById<ImageView>(R.id.imageuomo)
        val imageView2 = view.findViewById<ImageView>(R.id.imagedonna)
        val editTextNumber = view.findViewById<EditText>(R.id.editTextNumber)
        val editTextHeight = view.findViewById<EditText>(R.id.editTextHeight)
        val textpeso = view.findViewById<TextView>(R.id.textpeso)
        val textAltezza = view.findViewById<TextView>(R.id.textAltezza)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        // Pulire i campi EditText all'avvio
        editTextNumber.text.clear()
        editTextHeight.text.clear()

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

        saveButton.setOnClickListener {
            val peso = editTextNumber.text.toString()
            val altezza = editTextHeight.text.toString()

            if (peso.isNotEmpty() && altezza.isNotEmpty()) {
                val user = auth.currentUser

                if (user != null) {
                    val data = hashMapOf(
                        "peso" to peso,
                        "altezza" to altezza
                    )

                    db.collection("users").document(user.uid)
                        .set(data)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Peso e altezza salvati con successo")
                            Toast.makeText(requireContext(), "Peso e altezza salvati!", Toast.LENGTH_SHORT).show()
                            textpeso.text = peso
                            textAltezza.text = altezza
                            editTextNumber.text.clear() // Pulisce il campo EditText dopo il salvataggio
                            editTextHeight.text.clear() // Pulisce il campo EditText dopo il salvataggio
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Errore nel salvataggio del peso e altezza", e)
                            Toast.makeText(requireContext(), "Errore nel salvataggio del peso e altezza", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Log.w("Firebase", "Utente non autenticato")
                    Toast.makeText(requireContext(), "Utente non autenticato", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Inserisci il tuo peso e altezza", Toast.LENGTH_SHORT).show()
            }
        }

        // Recupera peso e altezza salvati da Firebase quando la vista viene creata
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val savedPeso = document.getString("peso")
                        val savedAltezza = document.getString("altezza")
                        savedPeso?.let {
                            textpeso.text = it
                        }
                        savedAltezza?.let {
                            textAltezza.text = it
                        }
                    } else {
                        Log.d("Firebase", "Nessun documento trovato")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Errore nel recupero del peso e altezza", e)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




