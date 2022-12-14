package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.quiz.data.ListaPreguntas
import com.example.quiz.databinding.ActivityPreguntaBinding
import com.example.quiz.modelos.Pregunta
import com.example.quiz.modelos.Respuesta
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ActivityPregunta : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntaBinding // Es la variable que está haciendo referencia al XML (activity_pregunta.xml)
    private var listaPreguntas: ArrayList<Pregunta> = arrayListOf(); // Variable de tipos lista que deja las preguntas de manera global sobre la clase
    private var valorOpcion: Int = 0 // opcion que selecciona el usuairo, si es cero, es porque no ha seleccionado nada
    private var posicionPregunta : Int = 0 // para identificar en preguntas va el quizz
    private lateinit var cronometro : CountDownTimer; // para definir el temporizador

    private lateinit var reproductorMusica: MediaPlayer // para definir el reproductor de música

    private lateinit var ventanaRespuestaErrada: AlertDialog // para definir la ventana "dialog" de respuesta errada

    private var back_pressed: Long = 0 // para confirmar, en caso de salir de la aplicación

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntaBinding.inflate(layoutInflater) // inflamos nuestro XML a la variable
        setContentView(binding.root) // lo agregamos la vista

        /**
         * Defininedo el cronometro en milisegundos 15 segundos y un intervalo de 1 segundo
         */
        cronometro = object : CountDownTimer(15000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.txtCuentaRegresiva.text = "Tiempo " + millisUntilFinished / 1000

                //El texto del cronometro se vuelve rojo cuando sea menor a 9
                if((millisUntilFinished / 1000).toInt() in 1..9){
                    binding.txtCuentaRegresiva.setTextColor(Color.RED)
                    binding.txtCuentaRegresiva.textSize = 55f // para que el texto se haga más grande cuando hay menos tiempo
                }
            }
            override fun onFinish() {
                binding.txtCuentaRegresiva.text = "El tiempo ha terminado"
                verificarMasPreguntasPorMostrar()
            }
        }

        //Reproducimos la música
        reproductorMusica = MediaPlayer.create(this, R.raw.quizmusica)
        reproductorMusica.start()
        binding.btnSonido.setOnClickListener {
            if(reproductorMusica.isPlaying){
                reproductorMusica.pause()
                binding.btnSonido.setImageDrawable(resources.getDrawable(R.drawable.ic_volume_plus))

            }else{
                reproductorMusica.start()
                binding.btnSonido.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_off))

            }

        }


        listaPreguntas = ListaPreguntas.obtenerListaPreguntas() as ArrayList<Pregunta> // obtenemos la lista de preguntas
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
                    // verificando que sea la repuesta correcta para mostrar o no la alerta de respuesta incorrecta
                    if(respuesta.esCorrecto && respuesta.opcionSeleccionadaPorUsuario){
                        //Selecciono la correcta
                        esCorrectoRepuesta = true
                    }
                }
                if(esCorrectoRepuesta){ // la pregunta seleccionada es correcta
                    verificarMasPreguntasPorMostrar()
                }else{ // NO selecciono la respuesta correcta
                    ventanaRespuestaErrada = MaterialAlertDialogBuilder(this).setTitle("Respuesta Incorrecta")
                        .setMessage(listaPreguntas[posicionPregunta].descripcionRespuesta)
                        .setCancelable(true)
                        .setNeutralButton("Aceptar", null)
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
        //Escondemos la imagen, pero en caso de tener imagen se habilita
        binding.imagenPregunta.visibility = View.GONE

        //válidando si la pregunta contiene imagen
        if(listaPreguntas[posicionPregunta].imagen != null){
            binding.imagenPregunta.visibility = View.VISIBLE
            //Utilizamos la librería para cargar imágenes de internet
            Glide
                .with(this)
                .load(listaPreguntas[posicionPregunta].imagen)
                .centerCrop()
                .into(binding.imagenPregunta)

        }
    }

    /**
     * Cuando seleccione la opción dejara todas las opciones por defecto
     * de manera tal que se vean igual y luego se agrega el estilo a la opción seleccionada
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun opcionSeleccionada(view: TextView, opcion :Int){

        desmarcarOpciones() // Esteticamente

        valorOpcion = opcion //guardando que opción selecciono el usuario

        // se marcar con un estilo diferente la opción seleccionada
        view.setBackgroundResource(R.drawable.respuesta_seleccionada)
        view.typeface= Typeface.DEFAULT_BOLD
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
            op.setBackgroundResource(R.drawable.opcion_estilo)
            op.typeface= Typeface.DEFAULT
        }
    }

    /**
     * Método que nos permite saber si hay más preguntas, ya que si no hay más deberá mostrarnos la pantalla de resultados
     */
    fun verificarMasPreguntasPorMostrar(){
        binding.txtCuentaRegresiva.textSize = 45f
        binding.txtCuentaRegresiva.setTextColor(Color.BLACK)

        if((listaPreguntas.size - 1) != posicionPregunta ){ // si pasa es por que aún hay más preguntas
            posicionPregunta++ // seguir con la siguiente pregunta
            valorOpcion = 0 // restablecer la opción
            definirPregunta() //Mostrar la siguiente pregunta

        }else{ // NO hay más preguntas por mostrar
            var intent = Intent(this, ResultadoActivity::class.java) // navegamos a la pantalla de resultados
            // enviamos las preguntas respondidas por el usuario hacia la otra pantalla
            intent.putParcelableArrayListExtra("preguntasRespondidas", listaPreguntas)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Nos permite saber cuando el usuario quiere retroceder de pantalla
     */
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()

        }else Toast.makeText(
            baseContext, "Pulse otra vez para salir", Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }

    /**
     * Cuando salgamos de la pantalla de de preguntas
     * Detenemos la música y cancelamos el temporizador
     */

    override fun onDestroy() {
        cronometro.cancel()
        reproductorMusica.pause()
        super.onDestroy()
    }
}