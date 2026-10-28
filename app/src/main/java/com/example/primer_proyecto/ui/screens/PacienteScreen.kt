package com.example.primer_proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primer_proyecto.model.Paciente
import com.example.primer_proyecto.viewmodel.viewmodel.PacienteViewModel

// Colores Personalizados Azul Rey
val AzulRey = Color(0xFF1A56DB)
val AzulReyContenedor = Color(0xFFDCE6FF)
val TextoAzulRey = Color(0xFF0F2B6B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacienteScreen(viewModel: PacienteViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "KotlinPet Manager 🐾",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulRey
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaSoporte()

            FormularioRegistro(viewModel = viewModel)

            HorizontalDivider()

            Text(
                text = "Pacientes Registrados (${viewModel.pacientes.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextoAzulRey
            )

            if (viewModel.pacientes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay pacientes registrados aún.",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.pacientes, key = { it.id }) { paciente ->
                        PacienteCard(
                            paciente = paciente,
                            onEliminar = { viewModel.eliminarPaciente(paciente) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaSoporte() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Soporte técnico",
                tint = AzulRey,
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text(
                    text = "Soporte Técnico",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextoAzulRey
                )
                Text(
                    text = "Para ayuda técnica, favor de comunicarse con soporte al +52 55 9876 5432 o escribir a soporte@kotlinpet.com",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun FormularioRegistro(viewModel: PacienteViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Registrar Nuevo Paciente",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AzulRey
        )

        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre de la mascota") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.especie,
                onValueChange = { viewModel.onEspecieChange(it) },
                label = { Text("Especie") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.raza,
                onValueChange = { viewModel.onRazaChange(it) },
                label = { Text("Raza") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = { viewModel.onEdadChange(it) },
                label = { Text("Edad (años)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.contactoEmergencia,
                onValueChange = { viewModel.onContactoChange(it) },
                label = { Text("Contacto emergencia") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.esUrgencia,
                onCheckedChange = { viewModel.onUrgenciaChange(it) },
                colors = CheckboxDefaults.colors(checkedColor = AzulRey)
            )
            Text(text = "Requiere atención de urgencia")
        }

        viewModel.mensajeError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = { viewModel.agregarPaciente() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AzulRey)
        ) {
            Text("Registrar Paciente", color = Color.White)
        }
    }
}

@Composable
fun PacienteCard(
    paciente: Paciente,
    onEliminar: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (paciente.esUrgencia) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                AzulReyContenedor
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = paciente.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (paciente.esUrgencia) {
                        MaterialTheme.colorScheme.onErrorContainer
                    } else {
                        TextoAzulRey
                    }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (paciente.esUrgencia) {
                        Text(
                            text = "🚨 URGENCIA",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    IconButton(onClick = onEliminar) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar paciente",
                            tint = Color.Black // Icono cambiado a color negro
                        )
                    }
                }
            }

            Text(text = "Especie: ${paciente.especie} | Raza: ${paciente.raza}")
            Text(text = "Edad: ${paciente.edad} año(s)")
            Text(text = "Contacto: ${paciente.contactoEmergencia}")
            Text(
                text = "Ingreso: ${paciente.fechaIngreso}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}