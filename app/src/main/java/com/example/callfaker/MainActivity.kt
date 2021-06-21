package com.example.callfaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var button: FloatingActionButton
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = choose_dp
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
    fun simulateCall(view: View) {
        val mobileNumber = number_text.editableText.toString()
        val callerName = name_text.editableText.toString()
        val s = time_text.editableText.toString()
        val delayTime : Long = (s.toLong())*1000
//        val profilePicture = profile_picture
        Handler().postDelayed({
            val intent = Intent(this,FakeRingingActivity::class.java)
            intent.putExtra(FakeRingingActivity.NUMBER_EXTRA,mobileNumber)
            intent.putExtra(FakeRingingActivity.NAME_EXTRA,callerName)
//        intent.putExtra(FakeRingingActivity.IMAGE_EXTRA,imageView)

            startActivity(intent)
            finish()
        },delayTime )
    }


}