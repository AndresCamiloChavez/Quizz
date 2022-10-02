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
                Pregunta("¿Quién es el autor de la frase 'Pienso, luego existo'?",
                    mutableListOf(
                        Respuesta("Descartes", true),
                        Respuesta("Platón", false),
                        Respuesta("Sócrates", false)
                    ),
                    // en caso de no tener una imagen hay que especificar el nombre de la propiedad
                    descripcionRespuesta = "'Je pense, donc je suis' es la frase original, escrita en francés, del filósofo René Descartes (1596-1650). Esta frase resume el pensamiento y el método de Descartes, para quien todo se inicia con la duda"
                ),
                Pregunta("¿Cuántos litros de sangre tiene una persona adulta?",
                    mutableListOf(
                        Respuesta("Tiene entre 2 y 4 litros", false),
                        Respuesta("Tiene entre 4 y 6 litros", true),
                        Respuesta("Tiene 10 litros", false)
                    ),
                    descripcionRespuesta = "La cantidad de sangre varía de persona a persona. El volumen de sangre representa del 7 al 8% del peso corporal. De esta forma, en una persona adulta que pesa entre 50 y 80 kilogramos, puede haber entre 4 y 6 litros de sangre."

                ),
                Pregunta("¿Cómo se llama el aeropuerto de Bogotá?",
                    mutableListOf(
                        Respuesta("El Dorado", true),
                        Respuesta("Aguas Claras", false),
                        Respuesta("Baracoa", false)
                    ),
                    descripcionRespuesta = "El Dorado Luis Carlos Galán Sarmiento más conocido como Aeropuerto de El Dorado, es el principal aeropuerto de Colombia. Se encuentra localizado dentro de Bogotá"

                ),
                Pregunta("¿Cuál es el país más grande y el más pequeño del mundo? ",
                    mutableListOf(
                        Respuesta("Rusia y Vaticano", true),
                        Respuesta("China y Nauru", false),
                        Respuesta("India y San Marino ", false)
                    ),
                    "https://www.infobae.com/new-resizer/1NstrfwipAGEN_wSRRhwcHApfVk=/1200x628/filters:format(webp):quality(85)//cloudfront-us-east-1.images.arcpublishing.com/infobae/ULKTOARPZBA4HNYABMXB22UIQ4.jpg",
                    descripcionRespuesta = "Rusia es el país mas grande del mundo con un área de 17 millones de km2, mientras el Vaticano tiene apenas 0,44 km2."
                )
            )
        )
        return lista.shuffled() // lista de preguntas aleatoriamente

    }
}