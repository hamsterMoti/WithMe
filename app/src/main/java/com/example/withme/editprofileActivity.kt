package com.example.withme

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class editprofileActivity : AppCompatActivity() {

    private val REQUEST_GALLERY_TAKE = 2

    private lateinit var userImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        userImage = findViewById<ImageView>(R.id.userImage)

        userImage.setOnClickListener {
            //ギャラリーを開きイメージに定義
            openGalleryForImage()
//            userImage.setImageBitmap(bitmap)
        }


    }

    //ギャラリーを開くためのメソッド
    private fun openGalleryForImage() {
        //ギャラリーに画面を遷移するためのIntent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }


    // onActivityResultにイメージ設定
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            2 -> {
                if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    //選択された写真にImageViewを変更
                    userImage.setImageURI(data?.data) // handle chosen image
                }
            }
        }
    }
//    fun convertImageToBitmap():{
//
//    }
}