package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.OkHttpClient

class withdrawalActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)

        var withdrawalButton = findViewById<Button>(R.id.withdrawalButton)
        var nowpasswordEdit = findViewById<EditText>(R.id.nowpasswordEdit)
        val emptyError = errormsg.emptyError

        //退会ボタンが押された時の処理
        withdrawalButton.setOnClickListener {

            if(nowpasswordEdit.text.toString().isEmpty() ){
                nowpasswordEdit.error = emptyError
            }else{

            }
        }


    }
}