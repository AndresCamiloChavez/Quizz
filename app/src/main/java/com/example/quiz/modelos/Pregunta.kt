package com.example.quiz.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Pregunta(
    val contenidoPregunta: String = "",
    val respuestas: List<Respuesta>,
    val estadoPregunta: Boolean, // para saber si falló o acertó la respuesta
): Parcelable
