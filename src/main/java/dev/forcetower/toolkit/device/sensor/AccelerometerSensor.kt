package dev.forcetower.toolkit.device.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import javax.inject.Inject

class AccelerometerSensor @Inject constructor(context: Context) :
    AndroidSensor<AccelerometerSensor.Axies>(
        context,
        Sensor.TYPE_ACCELEROMETER,
        PackageManager.FEATURE_SENSOR_ACCELEROMETER
    ) {

    data class Axies(val x: Float, val y: Float, val z: Float)

    override fun convert(event: SensorEvent): Axies = Axies(
        x = event.values[0],
        y = event.values[1],
        z = event.values[2]
    )

}