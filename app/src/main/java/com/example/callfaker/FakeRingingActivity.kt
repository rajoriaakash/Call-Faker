package com.example.callfaker

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fake_ringing.*
import kotlinx.android.synthetic.main.activity_main.*


class FakeRingingActivity : AppCompatActivity() {

    companion object{
        const val NUMBER_EXTRA = "number_extra"
        const val NAME_EXTRA = "name_extra"
    }
    private var ringTone: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_ringing)

        Initialize();
        StartRingTone();

        answercall.setOnClickListener{
            ringTone!!.stop()
        }

        rejectcall.setOnClickListener{
            ringTone!!.stop()
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
        }
    }

    private fun Initialize(){
        val callerNumber = intent.getStringExtra(NUMBER_EXTRA)!!
        if(callerNumber.isNotBlank()) caller_number.text = callerNumber

        val callerName = intent.getStringExtra(NAME_EXTRA)!!
        if(callerName.isNotBlank()) caller_name.text = callerName
    }

    private fun StartRingTone() {
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringTone = MediaPlayer.create(applicationContext, notification)
        ringTone!!.start()
    }
}