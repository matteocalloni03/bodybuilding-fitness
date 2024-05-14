package com.example.bodybuildingfitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bodybuildingfitness.databinding.ActivityLoginBinding
import com.example.bodybuildingfitness.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate((layoutInflater))

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this,navigationActivity::class.java)
        startActivity(intent)
    }
}