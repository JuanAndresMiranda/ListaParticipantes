package com.example.listaparticipantes

import com.example.listaparticipantes.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaparticipantes.ui.theme.ListaParticipantesTheme
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaParticipantesTheme {
                    ListaParticipantes()
                }
            }
        }
    }

data class Participante(
    val nombre: String,
    val alias: String,
    val profesion: String
)

@Composable
fun ListaParticipantes() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var alias by rememberSaveable { mutableStateOf("") }
    var profesion by rememberSaveable { mutableStateOf("") }
    val participantes = remember {
        mutableStateListOf<Participante>()
    }
    val formularioValido = nombre.isNotBlank() && alias.isNotBlank()


    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding()) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text("INSCRIPCIONES AL TORNEO")
        }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
        value = nombre,
        onValueChange = {nombre = it},
            modifier = Modifier.fillMaxWidth(),
        label = {Text("Nombre")}
    )
        OutlinedTextField(
            value = alias,
            onValueChange = {alias = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Alias")}
        )
        OutlinedTextField(
            value = profesion,
            onValueChange = {profesion = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Profesion")}
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.azul),
                contentColor = Color.White,
                disabledContainerColor = colorResource(R.color.azul),
                disabledContentColor = Color.White
            ),
            enabled = formularioValido,
            onClick = {
                if (formularioValido){
                    val nuevo = Participante(
                        nombre = nombre.trim(),
                        alias = alias.trim(),
                        profesion = profesion.trim()
                    )
                    participantes.add(nuevo)
                    nombre = ""
                    alias = ""
                    profesion = ""
                }
            }
        ) {
            Text("Añadir participante", color = Color.White)
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Participantes inscritos: ${participantes.size}")
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.rojo),
                    contentColor = Color.Black,
                    disabledContainerColor = colorResource(R.color.rojo),
                    disabledContentColor = Color.Black
                ),
                enabled = participantes.isNotEmpty(),
                onClick = { participantes.clear() }
            ) {
                Text("Eliminar todos")
            }
        }
    Spacer(Modifier.height(8.dp))

    LazyColumn(
        modifier = Modifier.weight(1f)
    ) {
        items(participantes) { participante ->
            TarjetaParticipante(
                participante,
                onEliminar = { participantes.remove(participante) }
            )
        }
        }

    }
    }


@Composable
fun TarjetaParticipante(participante: Participante, onEliminar: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = participante.nombre)
                Text(text = "Alias: ${participante.alias}")
                Text(text = participante.profesion)
            }
            Button(onClick = onEliminar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.rojo),
                    contentColor = Color.Black,
                    disabledContainerColor = colorResource(R.color.rojo),
                    disabledContentColor = Color.Black
                )
            ) {
                Text("Eliminar")
            }
            }
        }
    }
        @Preview(showBackground = true)
@Composable
fun ListaPreview() {
    ListaParticipantesTheme {
        ListaParticipantes()
    }
}