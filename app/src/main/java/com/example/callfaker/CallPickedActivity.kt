package com.example.callfaker

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.android.synthetic.main.activity_call_picked.*
import kotlinx.android.synthetic.main.activity_fake_ringing.*

class CallPickedActivity : AppCompatActivity() {companion object{
    const val NAME_EXTRA = "name_extra"
    const val IMAGE_EXTRA = "image_extra"
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_picked)

        caller_Name.text = intent.getStringExtra(NAME_EXTRA)
        val bitmap = intent.getParcelableExtra(FakeRingingActivity.IMAGE_EXTRA) as Bitmap?
        callPicked_dp.setImageBitmap(bitmap)

        val stopwatch = Stopwatch()
        stopwatch.setTextView(call_duration)
        stopwatch.start()

        endcall.setOnClickListener{
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
            finish()
        }
    }

}