package com.example.callfaker

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fake_ringing.*


class FakeRingingActivity : AppCompatActivity() {

    companion object{
        const val NUMBER_EXTRA = "number_extra"
        const val NAME_EXTRA = "name_extra"
        const val IMAGE_EXTRA = "image_extra"
    }
    var callerNumber : String = ""
    var callerName :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_ringing)

        initialize();
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val ringTone = MediaPlayer.create(applicationContext, notification)
        ringTone.isLooping = true
        ringTone!!.start()

        answercall.setOnClickListener{
            ringTone.stop()
            val intent = Intent(this,CallPickedActivity::class.java)
            intent.putExtra(CallPickedActivity.NAME_EXTRA,callerName)
            fakeRinging_dp.setDrawingCacheEnabled(true)
            val b: Bitmap = fakeRinging_dp.getDrawingCache()
            intent.putExtra(CallPickedActivity.IMAGE_EXTRA, b)
            startActivity(intent)
            finish()
        }

        rejectcall.setOnClickListener{
            ringTone.stop()
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
            finish()
        }
    }

    private fun initialize(){
        callerNumber = intent.getStringExtra(NUMBER_EXTRA)!!
        if(callerNumber.isNotBlank()) caller_number.text = callerNumber

        callerName = intent.getStringExtra(NAME_EXTRA)!!
        if(callerName.isNotBlank()) caller_name.text = callerName

        val bitmap = intent.getParcelableExtra(IMAGE_EXTRA) as Bitmap?
        fakeRinging_dp.setImageBitmap(bitmap)
    }


}