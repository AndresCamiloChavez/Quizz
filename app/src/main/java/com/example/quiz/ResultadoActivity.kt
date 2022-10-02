package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.databinding.ActivityResultadoBinding
import com.example.quiz.modelos.Pregunta
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter

class ResultadoActivity : AppCompatActivity() {

    private var back_pressed: Long = 0
    private lateinit var graficoCirular: PieChart // definimos el gráfico "dona"
    private lateinit var graficoBarras: BarChart // definimos el gráfico de barras

    private lateinit var binding: ActivityResultadoBinding // para conectar el XML con Kotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater) // para enlazar el XML y Kotlin
        setContentView(binding.root)// para enlazar el XML y Kotlin

        //Obtenemos la lista de preguntas respondidas
        val listaRespuestas = intent.extras!!.getSerializable("preguntasRespondidas") as ArrayList<Pregunta>

        consutruirGraficoCirular(listaRespuestas)
        construirGraficoBarras(listaRespuestas)

    }


    /**
     * Método que crea un gráfico de circular con las respuestas correctas e incorrectas
     */
    fun consutruirGraficoCirular( listaPreguntasConRespuestas: List<Pregunta>) {
        graficoCirular = binding.graficoCirularVista
        var respuestasCorrectas = 0
        var respuestasIncorretas = 0

        //recorremos la preguntas
        listaPreguntasConRespuestas.forEach { preguntas ->
            //recorremos las respuestas de las preguntas
            preguntas.respuestas.forEach { respuesta ->
                //validamos cuales son las respuestas correctas selccionadas por el usuario
                if (respuesta.esCorrecto && respuesta.opcionSeleccionadaPorUsuario) {
                    respuestasCorrectas++
                }
            }
        }
        //las respuestas incorrectas ---->  es el resultado del total de preguntas menos las correctas
        respuestasIncorretas = (listaPreguntasConRespuestas.size - respuestasCorrectas)
        // Es para poder arrastrar el gráfico
        graficoCirular.setDragDecelerationFrictionCoef(0.95f)
        //animación en el gráfico
        graficoCirular.animateY(1400, Easing.EaseInOutQuad)
        val entries: ArrayList<PieEntry> = ArrayList()
        // agregamos la parte de respuestas correctas e incorrectas
        entries.add(PieEntry(respuestasCorrectas.toFloat()))
        entries.add(PieEntry(respuestasIncorretas.toFloat()))
        val dataSet = PieDataSet(entries, "")
        // agregamos los colores que van a tener las secciones
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        dataSet.colors = colors

        val data = PieData(dataSet)
        graficoCirular.description.text = "";
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        graficoCirular.setData(data)
    }

    /**
     * Método que crea un gráfico de barras especificando que respuestas contesto bien o cuales no
     */
    fun construirGraficoBarras(listaPreguntasConRespuestas: List<Pregunta>){
        graficoBarras = binding.graficoBarrasVista
        var barEntriesList: ArrayList<BarEntry> = arrayListOf()
        var labels: ArrayList<String> = arrayListOf()
        listaPreguntasConRespuestas.forEachIndexed{ indice, pregunta ->
            labels.add("Pr ${indice+1}")
            barEntriesList.add(BarEntry(indice.toFloat(), 0f))
            pregunta.respuestas.forEach { respuesta ->
                //se marcan todas las respuestas como incorrectas, para luego actualizar en caso de encotrar una correcta
                if(respuesta.opcionSeleccionadaPorUsuario && respuesta.esCorrecto){
                    barEntriesList[indice] = BarEntry(indice.toFloat(), 1f) // Encontro una repuesta correcta
                    return@forEach
                }
            }
        }
        var barDataSet = BarDataSet(barEntriesList, "Preguntas contestadas correctamente")
        graficoBarras.xAxis.valueFormatter = IndexAxisValueFormatter(labels) // agregamos que pregunta corresponde a cada barra
        var barData = BarData(barDataSet)
        graficoBarras.data = barData
        graficoBarras.setScaleMinima(1.6f, 0.1f) // definimos un tamaño en especifo para que se vea
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(resources.getColor(R.color.purple_200))
        barDataSet.valueTextSize = 16f
        graficoBarras.description.isEnabled = false
    }
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            startActivity(Intent(this, MainActivity::class.java))
        }else Toast.makeText(
            baseContext, "Pulse otra vez para salir", Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }
}