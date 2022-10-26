package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class loginActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar //バーを隠す
            ?.hide()

        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountText = findViewById<TextView>(R.id.createAccountText)
        val userEdit = findViewById<EditText>(R.id.myIdEdit)
        val passEdit = findViewById<EditText>(R.id.passEdit)
        val emptyError = myApp.emptyError

        loginButton.setOnClickListener {
            if (userEdit.text.toString().isEmpty()) {
                userEdit.error = emptyError
            } else if (passEdit.text.toString().isEmpty()) {
                passEdit.error = emptyError
            } else {
                val myId = userEdit.text.toString()
                val pass = passEdit.text.toString()
//                URLを変更
                val URL = myApp.apiUrl + "loginAuth.php?userId=$myId&password=$pass"
                val request = Request.Builder().url(URL).build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        this@loginActivity.runOnUiThread {
                            Toast.makeText(applicationContext, myApp.connectionError, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val csvStr = response.body!!.string()
                        val resultError = JSONObject(csvStr)
                        if(resultError.getString("result") == "error") {
                            this@loginActivity.runOnUiThread {
                                Toast.makeText(applicationContext, myApp.notMatch, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }else if(resultError.getString("result") == "success"){
                            this@loginActivity.runOnUiThread {
                                val myApp = myApplication.getInstance()
                                myApp.loginUserId = myId
                                val intent = Intent(applicationContext, timelineActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                })
            }
        }


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