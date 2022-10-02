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
    private lateinit var graficoCirular: PieChart
    private lateinit var graficoBarras: BarChart

    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        listaPreguntasConRespuestas.forEach { preguntas ->
            preguntas.respuestas.forEach { respuesta ->
                if (respuesta.esCorrecto && respuesta.opcionSeleccionadaPorUsuario) {
                    respuestasCorrectas++
                }
            }
        }

        respuestasIncorretas = (listaPreguntasConRespuestas.size - respuestasCorrectas)

        graficoCirular.setDragDecelerationFrictionCoef(0.95f)
        graficoCirular.setDragDecelerationFrictionCoef(0.95f)

        //animación en el gráfico
        graficoCirular.animateY(1400, Easing.EaseInOutQuad)

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(respuestasCorrectas.toFloat()))
        entries.add(PieEntry(respuestasIncorretas.toFloat()))
        val dataSet = PieDataSet(entries, "")

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

        graficoBarras.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        var barData = BarData(barDataSet)
        graficoBarras.data = barData
        graficoBarras.setScaleMinima(1.6f, 0.1f)
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