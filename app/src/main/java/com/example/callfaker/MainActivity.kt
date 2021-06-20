package com.example.callfaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun simulateCall(view: View) {
        val mobileNumber = number_text.editableText.toString()
        val callerName = name_text.editableText.toString()
        val intent = Intent(this,FakeRingingActivity::class.java)
        intent.putExtra(FakeRingingActivity.NUMBER_EXTRA,mobileNumber)
        intent.putExtra(FakeRingingActivity.NAME_EXTRA,callerName)

        startActivity(intent)
    }


}