package com.example.quiz.modelos

data class Respuesta(
    val contenidoRespuesta: String = "",
    val esCorrecto: Boolean // para identificar la respuesta correcta
)
