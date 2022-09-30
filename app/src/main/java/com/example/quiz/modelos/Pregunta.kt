package com.example.quiz.modelos

data class Pregunta(
    val contenidoPregunta: String = "",
    val respuestas: List<Respuesta>,
    val estadoPregunta: Boolean, // para saber si falló o acertó la respuesta

)
