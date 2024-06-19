package com.example.bodybuildingfitness.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bodybuildingfitness.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class giornipersonalizzata : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var editTextScheda: EditText
    private lateinit var buttonSalva: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_giornipersonalizzata)

        // Inizializza Firebase Auth e Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inizializza le view
        editTextScheda = findViewById(R.id.editTextScheda)
        buttonSalva = findViewById(R.id.buttonSalva)

        val userId = auth.currentUser?.uid

        // Carica la scheda salvata se esiste
        userId?.let {
            db.collection("scheda").document(it).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    editTextScheda.setText(document.getString("scheda"))
                }
            }.addOnFailureListener { e ->
                Log.w("Firebase", "Errore nel recupero della scheda", e)
            }
        }

        // Imposta il listener per salvare la scheda
        buttonSalva.setOnClickListener {
            val scheda = editTextScheda.text.toString()

            if (scheda.isNotEmpty()) {
                val schedaData = hashMapOf(
                    "scheda" to scheda
                )

                userId?.let {
                    db.collection("scheda").document(it).set(schedaData)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Scheda salvata con successo")
                            Toast.makeText(this, "Scheda salvata!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Errore nel salvataggio della scheda", e)
                            Toast.makeText(this, "Errore nel salvataggio della scheda", Toast.LENGTH_SHORT).show()
                        }
                } ?: run {
                    Log.w("Firebase", "Utente non autenticato")
                    Toast.makeText(this, "Utente non autenticato", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Inserisci la tua scheda", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


