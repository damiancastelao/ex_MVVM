package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel(): ViewModel() {

    // etiqueta para logcat
    private val TAG_LOG = "miDebug"

    // estados del juego
    // usamos LiveData para que la IU se actualice
    // patron de diseño observer
    val estadoLiveData: MutableLiveData<Estados?> = MutableLiveData(Estados.INICIO)

    // cuenta atras
    // usamos LiveData para que la IU se actualice
    val cuentaAtrasLiveData: MutableLiveData<EstadosAuxiliares?> = MutableLiveData(EstadosAuxiliares.AUX1)

    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    var _numbers = mutableStateOf(0)

    // inicializamos variables cuando instanciamos
    init {
        // estado inicial
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${estadoLiveData.value}")
    }

    /**
     * crear entero random
     */
    fun crearRandom() {
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoLiveData.value = Estados.GENERANDO
        _numbers.value = (0..3).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoLiveData.value}")
        actualizarNumero(_numbers.value)
    }

    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoLiveData.value}")
        Datos.numero = numero
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoLiveData.value = Estados.ADIVINANDO
        // empieza la cuenta atras
        estadosAuxiliares()
    }

    /**
     * comprobar si el boton pulsado es el correcto
     * @param ordinal: Int numero de boton pulsado
     * @return Boolean si coincide TRUE, si no FALSE
     */
    fun comprobar(ordinal: Int): Boolean {

        Log.d(TAG_LOG, "comprobamos - Estado: ${estadoLiveData.value}")
        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "es correcto")
            estadoLiveData.value = Estados.INICIO
            Log.d(TAG_LOG, "GANAMOS - Estado: ${estadoLiveData.value}")
            true
        } else {
            Log.d(TAG_LOG, "no es correcto")
            estadoLiveData.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "otro intento - Estado: ${estadoLiveData.value}")
            false
        }
    }

    /**
     * Corutina que lanza estados auxiliares
     */
    fun estadosAuxiliares() {
        viewModelScope.launch {
            // iniciamos cuenta atras
            cuentaAtrasLiveData.value = EstadosAuxiliares.AUX5
            // empezamos la cuenta atras
            Log.d(TAG_LOG, "estado (corutina): ${cuentaAtrasLiveData.value}")
            delay(1500)
            cuentaAtrasLiveData.value = EstadosAuxiliares.AUX4
            Log.d(TAG_LOG, "estado (corutina): ${cuentaAtrasLiveData.value}")
            delay(1500)
            cuentaAtrasLiveData.value = EstadosAuxiliares.AUX3
            Log.d(TAG_LOG, "estado (corutina): ${cuentaAtrasLiveData.value}")
            delay(1500)
            cuentaAtrasLiveData.value = EstadosAuxiliares.AUX2
            Log.d(TAG_LOG, "estado (corutina): ${cuentaAtrasLiveData.value}")
            delay(1500)
            cuentaAtrasLiveData.value = EstadosAuxiliares.AUX1
            Log.d(TAG_LOG, "estado (corutina): ${cuentaAtrasLiveData.value}")
            delay(1500)
            // ponemos el estado en INICIO si llegamos al final de la cuenta atras
            estadoLiveData.value = Estados.INICIO
        }
    }
}