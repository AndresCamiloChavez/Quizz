package com.example.quiz

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.quiz.data.ListaPreguntas
import com.example.quiz.databinding.ActivityPreguntaBinding
import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ActivityPregunta : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntaBinding // Es la variable que está haciendo referencia al XML (activity_pregunta.xml)
    private var listaPreguntas: List<Pregunta> = mutableListOf(); // Variable de tipos lista que deja las preguntas de manera global sobre la clase
    private var valorOpcion: Int = 0
    private var posicionPregunta : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntaBinding.inflate(layoutInflater) // inflamos nuestro XML a la variable
        setContentView(binding.root) // lo agregamos la vista

        listaPreguntas = ListaPreguntas.obtenerListaPreguntas()

        definirPregunta()


        /**
         * Cuando se da clic sobre alguna de las opciones
         */

        binding.opcion1.setOnClickListener{
            opcionSeleccionada(binding.opcion1,1)
        }
        binding.opcion2.setOnClickListener{
            opcionSeleccionada(binding.opcion2,2)
        }
        binding.opcion3.setOnClickListener{
            opcionSeleccionada(binding.opcion3,3)
        }

        /**
         *Cuando se de clic en el botón de enviar
         */
        binding.btnEnviar.setOnClickListener {
            var esCorrectoRepuesta = false
            if(valorOpcion != 0 ){

                //validando que la opción seleccionada seá válida
                listaPreguntas[posicionPregunta].respuestas.forEach { respuesta: Respuesta ->
                    if(respuesta.esCorrecto && respuesta.opcionSeleccionadaPorUsuario){
                        //Selecciono la correcta
                        esCorrectoRepuesta = true
                    }
                }

                if(esCorrectoRepuesta){
                    if(listaPreguntas.size != posicionPregunta ){
                    //Mostrar la siguiente pregunta
                        posicionPregunta++
                        definirPregunta()
                    }else{
                        //Se acabo todas las preguntas
                    }
                }else{
                    //NO selecciono la respuesta correcta

                    val dialog = MaterialAlertDialogBuilder(this).setTitle("Respuesta Incorrecta")
                        .setCancelable(true)
                        .create()

                    dialog.show()

                    if(listaPreguntas.size != posicionPregunta ){
                        //Mostrar la siguiente pregunta
                        posicionPregunta++
                        definirPregunta()
                    }else{
                        //Se acabo todas las preguntas
                    }
                }

            }
        }

    }
    fun definirPregunta(){
        binding.txtContenidoPregunta.text = listaPreguntas[posicionPregunta].contenidoPregunta

        /**
         * Definiendo la primera pregunta en la pantalla
         **/
        if(listaPreguntas[posicionPregunta].respuestas.isNotEmpty()){
            binding.opcion1.text = listaPreguntas[posicionPregunta].respuestas[0].contenidoRespuesta
            if(listaPreguntas[posicionPregunta].respuestas.size > 1){
                binding.opcion2.text = listaPreguntas[posicionPregunta].respuestas[1].contenidoRespuesta
                if(listaPreguntas[posicionPregunta].respuestas.size > 2){
                    binding.opcion3.text = listaPreguntas[posicionPregunta].respuestas[2].contenidoRespuesta
                }
            }
        }
    }

    /**
     * Cuando seleccione la opción dejará todas las opciones por defecto
     * de manera tal que se vean igual y luego se agrega el estilo a la opción seleccionada
     */
    fun opcionSeleccionada(view: TextView, opcion :Int){

        agregarEstiloAOpcion()

        //guardando que opción selecciono el usuario
        valorOpcion = opcion

        view.setBackgroundColor(Color.parseColor("#70AAAA"))

        view.typeface= Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))

        //Definiendo que respuesta ha seleccionado el usuario
        listaPreguntas[posicionPregunta].respuestas.forEach { respuesta: Respuesta ->
            respuesta.opcionSeleccionadaPorUsuario = false
        }
        listaPreguntas[posicionPregunta].respuestas[opcion-1].opcionSeleccionadaPorUsuario = true

    }
    fun agregarEstiloAOpcion(){

        var optionList:ArrayList<TextView> = arrayListOf()

        optionList.add(0, binding.opcion1)
        optionList.add(1,binding.opcion2)
        optionList.add(2,binding.opcion3)

        for(op in optionList)
        {
            op.setTextColor(Color.parseColor("#555151"))
            op.setBackgroundColor(Color.parseColor("#70AAAA"))
            op.setBackgroundColor(Color.parseColor("#FF018786"))
            op.typeface= Typeface.DEFAULT
        }
    }
}