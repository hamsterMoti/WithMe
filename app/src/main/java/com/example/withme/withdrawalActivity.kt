package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

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

                //データ取得
                var apiUrl = myApp.apiUrl+"userDelete.php?userId="+myApp.loginMyId+"&password="+nowpasswordEdit.text
                Log.v("urldata",apiUrl)
                val request = Request.Builder().url(apiUrl).build()
                val errorText = "エラー"
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        this@withdrawalActivity.runOnUiThread {
                            Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val csvStr = response.body!!.string()
                        val resultError = JSONObject(csvStr)
                        if(resultError.getString("result") == "error") {
                            this@withdrawalActivity.runOnUiThread {
                            }
                        }else if(resultError.getString("result") == "success"){
                            this@withdrawalActivity.runOnUiThread {
                                Toast.makeText(applicationContext, "退会成功しました", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, loginActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                })


            }
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