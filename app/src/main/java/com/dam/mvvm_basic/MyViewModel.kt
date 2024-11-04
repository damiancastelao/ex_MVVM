package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel(): ViewModel() {

    // etiqueta para logcat
    private val TAG_LOG = "miDebug"

    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    var _numbers = mutableStateOf(0)

    // inicializamos variables cuando instanciamos
    init {
        // estado inicial
        Datos.estado = Estados.INICIO
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${Datos.estado.name}")
    }

    /**
     * crear entero random
     */
    fun crearRandom() {
        Datos.estado = Estados.GENERANDO
        _numbers.value = (0..3).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${Datos.estado.name}")
        actualizarNumero(_numbers.value)
    }

    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${Datos.estado.name}")
        Datos.numero = numero
        Datos.estado = Estados.ADIVINANDO
    }

    /**
     * comprobar si el boton pulsado es el correcto
     * @param ordinal: Int numero de boton pulsado
     * @return Boolean si coincide TRUE, si no FALSE
     */
    fun comprobar(ordinal: Int): Boolean {

        Log.d(TAG_LOG, "comprobamos - Estado: ${Datos.estado.name}")
        return if (ordinal == Datos.numero) {
            Datos.estado = Estados.INICIO
            Log.d(TAG_LOG, "es correcto")
            true
        } else {
            Datos.estado = Estados.ADIVINANDO
            Log.d(TAG_LOG, "no es correcto")
            false
        }
    }
}