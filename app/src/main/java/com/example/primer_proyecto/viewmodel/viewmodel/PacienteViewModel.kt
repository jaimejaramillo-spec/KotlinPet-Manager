package com.example.primer_proyecto.viewmodel.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.primer_proyecto.model.Paciente
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PacienteViewModel : ViewModel() {

    private val _pacientes = mutableStateListOf<Paciente>()
    val pacientes: List<Paciente> get() = _pacientes

    var nombre by mutableStateOf("")
        private set

    var especie by mutableStateOf("")
        private set

    var raza by mutableStateOf("")
        private set

    var edad by mutableStateOf("")
        private set

    var contactoEmergencia by mutableStateOf("")
        private set

    var esUrgencia by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    private var contadorId = 1

    fun onNombreChange(nuevoNombre: String) {
        nombre = nuevoNombre
        mensajeError = null
    }

    fun onEspecieChange(nuevaEspecie: String) {
        especie = nuevaEspecie
        mensajeError = null
    }

    fun onRazaChange(nuevaRaza: String) {
        raza = nuevaRaza
        mensajeError = null
    }

    fun onEdadChange(nuevaEdad: String) {
        if (nuevaEdad.isEmpty() || nuevaEdad.all { it.isDigit() }) {
            edad = nuevaEdad
            mensajeError = null
        }
    }

    fun onContactoChange(nuevoContacto: String) {
        contactoEmergencia = nuevoContacto
        mensajeError = null
    }

    fun onUrgenciaChange(nuevaUrgencia: Boolean) {
        esUrgencia = nuevaUrgencia
    }

    fun agregarPaciente() {
        if (nombre.isBlank() || especie.isBlank() || raza.isBlank() || edad.isBlank() || contactoEmergencia.isBlank()) {
            mensajeError = "Por favor completa todos los campos requeridos."
            return
        }

        val edadInt = edad.toIntOrNull()
        if (edadInt == null || edadInt <= 0) {
            mensajeError = "La edad debe ser un número entero mayor a 0."
            return
        }

        val formatoFecha = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        val fechaActual = formatoFecha.format(Date())

        val nuevoPaciente = Paciente(
            id = contadorId++,
            nombre = nombre.trim(),
            especie = especie.trim(),
            raza = raza.trim(),
            edad = edadInt,
            contactoEmergencia = contactoEmergencia.trim(),
            esUrgencia = esUrgencia,
            fechaIngreso = fechaActual
        )

        _pacientes.add(0, nuevoPaciente)
        limpiarFormulario()
    }

    fun eliminarPaciente(paciente: Paciente) {
        _pacientes.remove(paciente)
    }

    private fun limpiarFormulario() {
        nombre = ""
        especie = ""
        raza = ""
        edad = ""
        contactoEmergencia = ""
        esUrgencia = false
        mensajeError = null
    }
}