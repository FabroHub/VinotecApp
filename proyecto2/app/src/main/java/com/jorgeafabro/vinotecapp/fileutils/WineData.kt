package com.jorgeafabro.vinotecapp.fileutils

//  Clase del vino.

data class WineData(
    val id: String,
    val vino: String?,
    val tipo: String?,
    val pais: String?,
    val region: String?,
    val uva: String?,
    val url: String?,
    val precio: String
)
