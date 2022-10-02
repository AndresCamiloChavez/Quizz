package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.data.ListaPreguntas
import com.example.quiz.databinding.ActivityPreguntaBinding
import com.example.quiz.modelos.ListaPreguntasSerializable
import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ActivityPregunta : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntaBinding // Es la variable que está haciendo referencia al XML (activity_pregunta.xml)
    private var listaPreguntas: ArrayList<Pregunta> = arrayListOf(); // Variable de tipos lista que deja las preguntas de manera global sobre la clase
    private var valorOpcion: Int = 0
    private var posicionPregunta : Int = 0
    private lateinit var cronometro : CountDownTimer;

    private lateinit var ventanaRespuestaErrada: AlertDialog

    private var back_pressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntaBinding.inflate(layoutInflater) // inflamos nuestro XML a la variable
        setContentView(binding.root) // lo agregamos la vista

        /**
         * Defininedo el cronometro en milisegundos 30 segundos y un intervalo de 1 segundo
         */
        cronometro = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.txtCuentaRegresiva.text = "Tiempo " + millisUntilFinished / 1000

            }
            override fun onFinish() {
                binding.txtCuentaRegresiva.text = "El tiempo ha terminado"
                verificarMasPreguntasPorMostrar()
            }
        }

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

            //si es cero (0), es porque no ha seleccionado ninguna opción y por lo tanto no lo deja seguir
            if(valorOpcion != 0 ){

                //validando que la opción seleccionada seá válida
                listaPreguntas[posicionPregunta].respuestas.forEach { respuesta: Respuesta ->
                    if(respuesta.esCorrecto && respuesta.opcionSeleccionadaPorUsuario){
                        //Selecciono la correcta
                        esCorrectoRepuesta = true
                    }
                }

                if(esCorrectoRepuesta){ // la pregunta seleccionada es correcta
                    verificarMasPreguntasPorMostrar()
                }else{ // NO selecciono la respuesta correcta
                    ventanaRespuestaErrada = MaterialAlertDialogBuilder(this).setTitle("Respuesta Incorrecta")
                        .setMessage("La respuesta es ya que tal y tal motivo")
                        .setCancelable(true)
                        .create()
                    ventanaRespuestaErrada.show()

                    verificarMasPreguntasPorMostrar()
                }
            }
        }

    }

    fun definirPregunta(){
        binding.txtContenidoPregunta.text = listaPreguntas[posicionPregunta].contenidoPregunta
        desmarcarOpciones() // Esteticamente

        cronometro.cancel()
        cronometro.start()

        /**
         * Definiendo las opciones de la pregunta en la pantalla
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
     * Cuando seleccione la opción dejara todas las opciones por defecto
     * de manera tal que se vean igual y luego se agrega el estilo a la opción seleccionada
     */
    fun opcionSeleccionada(view: TextView, opcion :Int){

        desmarcarOpciones() // Esteticamente

        valorOpcion = opcion //guardando que opción selecciono el usuario

        // se marcar con un estilo diferente la opción seleccionada
        view.setBackgroundColor(Color.parseColor("#70AAAA"))
        view.typeface= Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))

        //Definiendo todas las respuestas del usuario como incorrectas, porque puede en la misma pregunta marcar y desmarcar diferentes opciones
        listaPreguntas[posicionPregunta].respuestas.forEach { respuesta: Respuesta ->
            respuesta.opcionSeleccionadaPorUsuario = false
        }
        //Actualizamos la respuesta seleccionada por el usuario como true, para identificar cual fue la seleccionada
        listaPreguntas[posicionPregunta].respuestas[opcion-1].opcionSeleccionadaPorUsuario = true

    }

    fun desmarcarOpciones(){

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
    fun verificarMasPreguntasPorMostrar(){

        if((listaPreguntas.size - 1) != posicionPregunta ){ // si pasa es por que aún hay más preguntas
            posicionPregunta++ // seguir con la siguiente pregunta
            valorOpcion = 0 // restablecer la opción
            definirPregunta() //Mostrar la siguiente pregunta

        }else{ // NO hay más preguntas por mostrar
            var intent = Intent(this, ResultadoActivity::class.java)
            intent.putParcelableArrayListExtra("preguntasRespondidas", listaPreguntas)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed() else Toast.makeText(
            baseContext, "Pulse otra vez para salir", Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}