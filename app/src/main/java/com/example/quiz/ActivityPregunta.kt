package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityPreguntaBinding

class ActivityPregunta : AppCompatActivity() {
    private lateinit var binding: ActivityPreguntaBinding // Es la variable que está haciendo referencia al XML (activity_pregunta.xml)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntaBinding.inflate(layoutInflater) // inflamos nuestro XML a la variable
        setContentView(binding.root) // lo agregamos la vista
    }
}