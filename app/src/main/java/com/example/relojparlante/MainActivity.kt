package com.example.relojparlante

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RelojApp()
            }
        }
    }

    @Composable
    fun RelojApp() {
        var horaActual by remember { mutableStateOf(obtenerHoraActual()) }
        var fechaActual by remember { mutableStateOf(obtenerFechaActual()) }
        var isPressed by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val buttonColor by animateColorAsState(
            targetValue = if (isPressed) Color(0xFF004D40) else Color(0xFF009688),
            label = "buttonColorAnim"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1A237E), Color(0xFF512DA8))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ciudad de México, CDMX (GMT-6)",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mostrar día y fecha completa
                Text(
                    text = fechaActual,
                    color = Color(0xFFB3E5FC),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = horaActual,
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        scope.launch {
                            isPressed = true
                            horaActual = obtenerHoraActual()
                            fechaActual = obtenerFechaActual()
                            reproducirHora(horaActual)
                            delay(1000)
                            isPressed = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .width(200.dp)
                        .height(60.dp)
                ) {
                    Text("Dime la hora", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }

    // Devuelve la hora actual formateada
    private fun obtenerHoraActual(): String {
        val calendar = Calendar.getInstance()
        val hora = calendar.get(Calendar.HOUR)
        val minuto = calendar.get(Calendar.MINUTE)
        val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
        val horaMostrada = if (hora == 0) 12 else hora
        return String.format("%d:%02d %s", horaMostrada, minuto, amPm)
    }

    // Devuelve día y fecha
    private fun obtenerFechaActual(): String {
        val locale = Locale("es", "MX")
        val dateFormat = SimpleDateFormat("EEEE, d 'de' MMMM 'del' yyyy", locale)
        val fecha = dateFormat.format(Date())
        return fecha.replaceFirstChar { it.uppercase() }
    }

    private fun reproducirHora(horaTexto: String) {
        val calendar = Calendar.getInstance()
        val hora24 = calendar.get(Calendar.HOUR_OF_DAY)
        val hora = calendar.get(Calendar.HOUR)
        val minuto = calendar.get(Calendar.MINUTE)

        val listaAudios = mutableListOf<Int>()

        if (hora == 1) {
            listaAudios.add(R.raw.es_la)
        } else {
            listaAudios.add(R.raw.son_las)
        }

        when (hora) {
            1 -> listaAudios.add(R.raw.uno)
            2 -> listaAudios.add(R.raw.dos)
            3 -> listaAudios.add(R.raw.tres)
            4 -> listaAudios.add(R.raw.cuatro)
            5 -> listaAudios.add(R.raw.cinco)
            6 -> listaAudios.add(R.raw.seis)
            7 -> listaAudios.add(R.raw.siete)
            8 -> listaAudios.add(R.raw.ocho)
            9 -> listaAudios.add(R.raw.nueve)
            10 -> listaAudios.add(R.raw.diez)
            11 -> listaAudios.add(R.raw.once)
            0, 12 -> listaAudios.add(R.raw.doce)
        }

        when (hora24) {
            in 0..11 -> listaAudios.add(R.raw.manana)   // 12 AM - 11:59 AM → de la mañana
            in 12..18 -> listaAudios.add(R.raw.tarde)   // 12 PM - 6:59 PM → de la tarde
            in 19..23 -> listaAudios.add(R.raw.noche)   // 7 PM - 11:59 PM → de la noche
        }

        listaAudios.add(R.raw.con)

        when (minuto) {
            0 -> listaAudios.add(R.raw.cero)
            1 -> listaAudios.add(R.raw.uno)
            2 -> listaAudios.add(R.raw.dos)
            3 -> listaAudios.add(R.raw.tres)
            4 -> listaAudios.add(R.raw.cuatro)
            5 -> listaAudios.add(R.raw.cinco)
            6 -> listaAudios.add(R.raw.seis)
            7 -> listaAudios.add(R.raw.siete)
            8 -> listaAudios.add(R.raw.ocho)
            9 -> listaAudios.add(R.raw.nueve)
            10 -> listaAudios.add(R.raw.diez)
            11 -> listaAudios.add(R.raw.once)
            12 -> listaAudios.add(R.raw.doce)
            13 -> listaAudios.add(R.raw.trece)
            14 -> listaAudios.add(R.raw.catorce)
            15 -> listaAudios.add(R.raw.quince)
            16 -> listaAudios.add(R.raw.dieciseis)
            17 -> listaAudios.add(R.raw.diecisiete)
            18 -> listaAudios.add(R.raw.dieciocho)
            19 -> listaAudios.add(R.raw.diecinueve)
            20 -> listaAudios.add(R.raw.veinte)
            21 -> listaAudios.add(R.raw.veintiuno)
            22 -> listaAudios.add(R.raw.veintidos)
            23 -> listaAudios.add(R.raw.veintitres)
            24 -> listaAudios.add(R.raw.veinticuatro)
            25 -> listaAudios.add(R.raw.veinticinco)
            26 -> listaAudios.add(R.raw.veintiseis)
            27 -> listaAudios.add(R.raw.veintisiete)
            28 -> listaAudios.add(R.raw.veintiocho)
            29 -> listaAudios.add(R.raw.veintinueve)
            30 -> listaAudios.add(R.raw.treinta)
            31 -> listaAudios.add(R.raw.treintayuno)
            32 -> listaAudios.add(R.raw.treintaydos)
            33 -> listaAudios.add(R.raw.treintaytres)
            34 -> listaAudios.add(R.raw.treintaycuatro)
            35 -> listaAudios.add(R.raw.treintaycinco)
            36 -> listaAudios.add(R.raw.treintayseis)
            37 -> listaAudios.add(R.raw.treintaysiete)
            38 -> listaAudios.add(R.raw.treintayocho)
            39 -> listaAudios.add(R.raw.treintaynueve)
            40 -> listaAudios.add(R.raw.cuarenta)
            41 -> listaAudios.add(R.raw.cuarentayuno)
            42 -> listaAudios.add(R.raw.cuarentaydos)
            43 -> listaAudios.add(R.raw.cuarentaytres)
            44 -> listaAudios.add(R.raw.cuarentaycuatro)
            45 -> listaAudios.add(R.raw.cuarentaycinco)
            46 -> listaAudios.add(R.raw.cuarentayseis)
            47 -> listaAudios.add(R.raw.cuarentaysiete)
            48 -> listaAudios.add(R.raw.cuarentayocho)
            49 -> listaAudios.add(R.raw.cuarentaynueve)
            50 -> listaAudios.add(R.raw.cincuenta)
            51 -> listaAudios.add(R.raw.cincuentayuno)
            52 -> listaAudios.add(R.raw.cincuentaydos)
            53 -> listaAudios.add(R.raw.cincuentaytres)
            54 -> listaAudios.add(R.raw.cincuentaycuatro)
            55 -> listaAudios.add(R.raw.cincuentaycinco)
            56 -> listaAudios.add(R.raw.cincuentayseis)
            57 -> listaAudios.add(R.raw.cincuentaysiete)
            58 -> listaAudios.add(R.raw.cincuentayocho)
            59 -> listaAudios.add(R.raw.cincuentaynueve)
        }

        listaAudios.add(R.raw.minutos)

        reproducirSecuencia(listaAudios)
    }

    // Reproduce los audios uno tras otro
    private fun reproducirSecuencia(lista: List<Int>, index: Int = 0) {
        if (index >= lista.size) return
        val player = MediaPlayer.create(this, lista[index])
        player.setOnCompletionListener {
            player.release()
            reproducirSecuencia(lista, index + 1)
        }
        player.start()
    }
}
