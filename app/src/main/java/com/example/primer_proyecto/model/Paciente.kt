package com.example.primer_proyecto.model

data class Paciente(
    val id: Int,
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: Int,
    val contactoEmergencia: String,
    val esUrgencia: Boolean,
    val fechaIngreso: String
)