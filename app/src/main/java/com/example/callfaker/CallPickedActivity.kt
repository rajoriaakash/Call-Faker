package com.example.callfaker

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import androidx.appcompat.app.AppCompatActivity
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.android.synthetic.main.activity_call_picked.*


class CallPickedActivity : AppCompatActivity() , SensorEventListener{companion object{
    const val NAME_EXTRA = "name_extra"
    const val IMAGE_EXTRA = "image_extra"

}

    private lateinit var sensorManager: SensorManager
    private var proximitySensor : Sensor? = null
    private var powerManager: PowerManager? = null
    private var wakeLock: WakeLock? = null
    private var field = 0x00000020
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_picked)

        caller_Name.text = intent.getStringExtra(NAME_EXTRA)
        val bitmap = intent.getParcelableExtra(FakeRingingActivity.IMAGE_EXTRA) as Bitmap?
        callPicked_dp.setImageBitmap(bitmap)

        val stopwatch = Stopwatch()
        stopwatch.setTextView(call_duration)
        stopwatch.start()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        try {
            // Yeah, this is hidden field.
            field = PowerManager::class.java.javaClass.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK")
                .getInt(null)
        } catch (ignored: Throwable) {
        }

        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager!!.newWakeLock(field, localClassName)

        endcall.setOnClickListener{
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
            finish()
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        val distance = event.values[0]
        if(distance.equals(0)){
            if(wakeLock?.isHeld() == true) {
                wakeLock?.release();
            }

        }else{
            if(!wakeLock?.isHeld()!!) {
                wakeLock?.acquire();
            }
        }
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()

        proximitySensor?.also { proximity ->
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}