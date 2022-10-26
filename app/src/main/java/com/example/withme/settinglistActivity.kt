package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class settinglistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settinglist)

        val tuutiText = findViewById<TextView>(R.id.tuutiText)
        val outText = findViewById<TextView>(R.id.outText)
        val blockText = findViewById<TextView>(R.id.blockText)

        //通知設定タップ時処理
        tuutiText.setOnClickListener{
            intent = Intent(applicationContext, notificationsettingActivity::class.java)
            startActivity(intent)
        }
        //退会タップ時処理
        outText.setOnClickListener{
            intent = Intent(applicationContext, withdrawalActivity::class.java)
            startActivity(intent)
        }
        //ブロック一覧表タップ時処理
        blockText.setOnClickListener {
            intent = Intent(applicationContext, blockActivity::class.java)
            startActivity(intent)

        }
    }
}