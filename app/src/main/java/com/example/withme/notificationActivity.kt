package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class notificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val client = OkHttpClient()
        val myApp = myApplication.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification2)

        val notificationRecycle = findViewById<RecyclerView>(R.id.notificationRecycle)



        //var apiUrl = myApp.apiUrl
        var apiUrl = "http://100.26.59.120/with_me/notifyList.php?userId=2200166@ecc.ac.jp"
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        // Log.v("blockurl",apiUrl)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@notificationActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {

                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)


                if(resultError.getString("result") == "error") {
                    this@notificationActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "succes"){
                    this@notificationActivity.runOnUiThread {
                        Log.v("blockurl",apiUrl)
                        val date =resultError.getJSONArray("blockList")
                        val listlisrt = mutableListOf<notificationDate>()

                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)

                            var notifyContent = json.getString("notifyContent")
                            var notifyDate = json.getString("notifyDate")
                            
                            Log.v("blockinfo","notifyContent:" + notifyContent+"notifyDate"+notifyDate)
                            listlisrt.add(notificationDate(1,notifyContent))

                        }

                        notificationRecycle.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = notificationAdapter(listlisrt,this@notificationActivity)
                        notificationRecycle.adapter = adapter

                    }
                }
            }
        })





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