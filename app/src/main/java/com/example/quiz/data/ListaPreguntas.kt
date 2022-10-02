package com.example.quiz.data

import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta

object ListaPreguntas {
    fun obtenerListaPreguntas(): ArrayList<Pregunta>{

        var lista: ArrayList<Pregunta> = arrayListOf();


        lista.addAll(
            listOf(
                Pregunta("¿1 Quién fundó Santa Marta, capital del departamento Magdalena en Colombia? ",
                mutableListOf(
                    Respuesta("Rodrigo de Bastidas", false),
                    Respuesta("Fransisco", true),
                    Respuesta("Camilo ", false)
                ),false
            ),
                Pregunta("¿2 Entre que siglos fue construida gran la muralla china ?",
                    mutableListOf(
                        Respuesta("1982", true),
                        Respuesta("1898", false),
                        Respuesta("1931", false)
                    ),false
                ),
                Pregunta("¿3 -> ¿Cuál es la forma de gobierno de Colombia?",
                    mutableListOf(
                        Respuesta("República presidencialista", true),
                        Respuesta("Congreso", false),
                        Respuesta("Ninguno", false)
                    ),false
                ),
                Pregunta("¿Cómo se llama el aeropuerto de Bogotá?",
                    mutableListOf(
                        Respuesta("El Dorado", false),
                        Respuesta("Aguas Claras", false),
                        Respuesta("Baracoa", true)
                    ),false
                ),
                Pregunta("¿1 Quién fundó Santa Marta, capital del departamento Magdalena en Colombia? ",
                    mutableListOf(
                        Respuesta("Rodrigo de Bastidas", false),
                        Respuesta("Fransisco", true),
                        Respuesta("Camilo ", false)
                    ),false
                ),
            )
        )
        return lista;

    }
}