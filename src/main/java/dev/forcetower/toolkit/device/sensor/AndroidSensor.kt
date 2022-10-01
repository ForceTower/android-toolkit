package dev.forcetower.toolkit.device.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch


abstract class AndroidSensor<T>(context: Context, typeSensor: Int, systemFeature: String) {

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var sensor: Sensor = sensorManager.getDefaultSensor(typeSensor)

    private val doesSensorExist: Boolean =
        context.packageManager.hasSystemFeature(systemFeature)

    abstract fun convert(event: SensorEvent): T

    fun observe(): Flow<T> {
        if (!doesSensorExist) {
            return emptyFlow()
        }
        return channelFlow {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event != null) {
                        trySend(convert(event))
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
            }
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

            awaitClose {
                sensorManager.unregisterListener(listener)
            }
        }
    }
}