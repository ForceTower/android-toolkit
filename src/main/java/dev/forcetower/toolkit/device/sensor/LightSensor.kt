package dev.forcetower.toolkit.device.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import javax.inject.Inject

class LightSensor @Inject constructor(context: Context) :
    AndroidSensor<Float>(
        context,
        Sensor.TYPE_LIGHT,
        PackageManager.FEATURE_SENSOR_LIGHT
    ) {

    override fun convert(event: SensorEvent): Float = event.values[0]
}