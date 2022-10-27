package com.example.withme

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class postActivity : AppCompatActivity() {

    private val REQUEST_GALLERY_TAKE = 2
    private lateinit var photo: ImageView
    private lateinit var recruitDaySp: TextView
    var cal: Calendar = Calendar.getInstance()
    // 年を取得
    val yyyy = cal.get(Calendar.YEAR);
    // 月を取得(ただし0〜11のため、1を加算する)
    val mm = cal.get(Calendar.MONTH)
    // 日を取得
    val dd = cal.get(Calendar.DAY_OF_MONTH);

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        photo = findViewById<ImageView>(R.id.photo)
        recruitDaySp = findViewById<TextView>(R.id.recruitDaySp)
        val titleEdit = findViewById<EditText>(R.id.titleEdit)
        val categrySp = findViewById<Spinner>(R.id.categrySp)
        val contentEdit = findViewById<EditText>(R.id.contentEdit)
        val postButton = findViewById<Button>(R.id.postButton)

        //画像タップ時の処理
        photo.setOnClickListener {
            openGalleryForImage()
        }

        //スピナー登録
        val array = arrayOf("未選択", "料理・グルメ", "相談")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        categrySp.adapter = arrayAdapter
        //スピナー選択取得
        //val item = categrySp.selectedItem.toString()

        //募集日時textタップ処理
        recruitDaySp.setOnClickListener{
            showDatePicker()
        }

        postButton.setOnClickListener {

            val item = categrySp.selectedItem.toString()
            Log.v("alldata","タイトル"+titleEdit.text.toString()+"カテゴリ"+item+"日時"+recruitDaySp.text.toString()+"内容"+contentEdit.text.toString())
        }





    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                recruitDaySp.text = "${year}/${month + 1}/${dayOfMonth}"
            },
            yyyy,
            mm,
            dd)
        datePickerDialog.show()
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
                    photo.setImageURI(data?.data) // handle chosen image
                }
            }
        }
    }

}