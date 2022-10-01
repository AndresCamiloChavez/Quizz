package com.example.quiz.data

import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta

object ListaPreguntas {
    fun obtenerListaPreguntas(): List<Pregunta>{

        var lista: MutableList<Pregunta> = mutableListOf();


        lista.addAll(
            listOf(
                Pregunta("¿Entre que siglos fue construida gran la muralla china ?",
                mutableListOf(
                    Respuesta("2", false),
                    Respuesta("1", true),
                    Respuesta("3", false)
                ),false
            ),
                Pregunta("¿2Entre que siglos fue construida gran la muralla china ?",
                    mutableListOf(
                        Respuesta("2", true),
                        Respuesta("1", false),
                        Respuesta("3", false)
                    ),false
                ),
                Pregunta("¿3 -> Entre que siglos fue construida gran la muralla china ?",
                    mutableListOf(
                        Respuesta("2", true),
                        Respuesta("1", false),
                        Respuesta("3", false)
                    ),false
                ),
                Pregunta("¿4 Entre que siglos fue construida gran la muralla china ?",
                    mutableListOf(
                        Respuesta("2", false),
                        Respuesta("1", false),
                        Respuesta("3", true)
                    ),false
                )
            )
        )
        return lista;

    }
}