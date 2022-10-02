package com.example.quiz.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Pregunta(
    val contenidoPregunta: String = "",
    val respuestas: List<Respuesta>,
    val imagen: String? // aquí se agrega la url de la imagen (tiene que ser una imagen válida), pero puede ser null o sea es opcional
): Parcelable
