package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountText = findViewById<TextView>(R.id.createAccountText)


        createAccountText.setOnClickListener {
            val intent = Intent(this, profileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val timeline = Intent(this, timelineActivity::class.java)
        val chat = Intent(this, chatActivity::class.java)
        val notification = Intent(this, notificationActivity::class.java)
        val mypage = Intent(this, mypageActivity::class.java)
        val setting = Intent(this, settingActivity::class.java)
        val login = Intent(this, loginActivity::class.java)

        //連携処理を実施
        when (item.itemId) {
            R.id.timeline -> startActivity(timeline)
            R.id.chat -> startActivity(chat)
            R.id.notification -> startActivity(notification)
            R.id.mypage -> startActivity(mypage)
            R.id.setting -> startActivity(setting)
            else -> startActivity(login)//ログアウト処理を書く
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}