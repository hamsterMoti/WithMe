package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class withdrawalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)

        var withdrawalButton = findViewById<Button>(R.id.withdrawalButton)
        var nowpasswordEdit = findViewById<EditText>(R.id.nowpasswordEdit)

        //退会ボタンが押された時の処理
        withdrawalButton.setOnClickListener {

            if(nowpasswordEdit.text.toString().isEmpty() ){
                Toast.makeText(applicationContext, "入力値がありません", Toast.LENGTH_SHORT).show()
            }else{

            }
        }


    }
}