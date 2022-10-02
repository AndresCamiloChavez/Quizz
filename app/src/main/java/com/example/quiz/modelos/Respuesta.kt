package com.example.quiz.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Respuesta(
    val contenidoRespuesta: String = "",
    val esCorrecto: Boolean, // para identificar la respuesta correcta
    var opcionSeleccionadaPorUsuario: Boolean = false, // Es para saber si el usuario tiene seleccionada esta respuesta
): Parcelable
