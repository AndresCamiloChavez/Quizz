package com.example.quiz.data

import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta

object ListaPreguntas {
    fun obtenerListaPreguntas(): List<Pregunta>{
        var lista: ArrayList<Pregunta> = arrayListOf();

        lista.addAll(
            listOf(
                Pregunta("¿Cúal es la capital de Colombia?",
                mutableListOf(
                    Respuesta("Caracas", false),
                    Respuesta("Bogotá", true),
                    Respuesta("Tunja ", false)
                ), "https://upload.wikimedia.org/wikipedia/commons/2/24/Bogot%C3%A1_Colpatria_Night.jpg",
                    "Oficialmente Bogotá, Distrito Capital (antiguamente, Santafé de Bogotá)"
            ),
                Pregunta("¿Entre que siglos fue construida gran la muralla china ?",
                    mutableListOf(
                        Respuesta("1982", true),
                        Respuesta("1898", false),
                        Respuesta("1931", false)
                    ),
                    descripcionRespuesta = "Porque es una de las capilates más grandes" // en caso de no tener una imagen hay que especificar el nombre de la propiedad
                ),
                Pregunta("¿3 -> ¿Cuál es la forma de gobierno de Colombia?",
                    mutableListOf(
                        Respuesta("República presidencialista", true),
                        Respuesta("Congreso", false),
                        Respuesta("Ninguno", false)
                    ),
                    descripcionRespuesta = "Porque es una de las capilates más grandes"

                ),
                Pregunta("4 ¿Cómo se llama el aeropuerto de Bogotá?",
                    mutableListOf(
                        Respuesta("El Dorado", false),
                        Respuesta("Aguas Claras", false),
                        Respuesta("Baracoa", true)
                    ),
                    descripcionRespuesta = "Porque es una de las capilates más grandes"

                ),
                Pregunta("¿5 Quién fundó Santa Marta, capital del departamento Magdalena en Colombia? ",
                    mutableListOf(
                        Respuesta("Rodrigo de Bastidas", false),
                        Respuesta("Fransisco", true),
                        Respuesta("Camilo ", false)
                    ),
                    descripcionRespuesta = "Porque es una de las capilates más grandes"
                )
            )
        )
        return lista.shuffled() // lista de preguntas aleatoriamente

    }
}