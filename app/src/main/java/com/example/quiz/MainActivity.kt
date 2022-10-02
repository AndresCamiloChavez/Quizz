package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Es la variable que está haciendo referencia al XML (activity_main.xml)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inflamos nuestro XML a la variable
        setContentView(binding.root) // lo agregamos la vista


        binding.btnJugar.setOnClickListener { vista -> // cuando se da clic sobre en el botón de Jugar
            val intent = Intent(this, ActivityPregunta::class.java) // definimos a que pantalla ir
            startActivity(intent) // inicia la nueva actividad
            finish()
        }

    }
}