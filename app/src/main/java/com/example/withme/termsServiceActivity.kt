package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient

class termsServiceActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_service)

        val bodyText2 = findViewById<TextView>(R.id.bodyText2)
        bodyText2.text = resources.getString(R.string.bodyTxt)
        val agreeCheckBox = findViewById<CheckBox>(R.id.agreeCheckBox)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // チェックされたらタイムライン画面に遷移する
        nextButton.setOnClickListener{
            if(agreeCheckBox.isChecked){
//             共通処理の値を呼び出し(URL,nickname,loginMyId,year,month,day,gender)
//             共通処理の値をDBに保存
//             http接続開始
                val intent = Intent(this, timelineActivity::class.java)
                startActivity(intent)
            }
        }


    }
}