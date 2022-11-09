package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val timeline = Intent(this, timelineActivity::class.java)
        val chat = Intent(this, chatlistActivity::class.java)
        val notification = Intent(this, notificationActivity::class.java)
        val mypage = Intent(this, mypageActivity::class.java)
        val settingsettinglist = Intent(this, settinglistActivity::class.java)
        val login = Intent(this, loginActivity::class.java)

        //連携処理を実施
        when (item.itemId) {
            R.id.timeline -> startActivity(timeline)
            R.id.chat -> startActivity(chat)
            R.id.notification -> startActivity(notification)
            R.id.mypage -> startActivity(mypage)
            R.id.setting -> startActivity(settingsettinglist)
            else -> startActivity(login)//ログアウト処理を書く
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}