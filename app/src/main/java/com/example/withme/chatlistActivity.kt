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

class chatlistActivity  : AppCompatActivity() {
    val myApp = myApplication.getInstance()
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlist)

        chatlistActivitysub(myApp)

    }

    fun chatlistActivitysub(myapp:myApplication){

        val chatlistRecycle = findViewById<RecyclerView>(R.id.chatlistRecycle)
        val countList = mutableListOf<talklinedata>()
        //recycleviewの処理
        var apiUrl = myApp.apiUrl+"chatList.php?userId="+myApp.loginMyId
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        Log.v("blockurl", apiUrl.toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@chatlistActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@chatlistActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "succes"){
                    this@chatlistActivity.runOnUiThread {
                        val date =resultError.getJSONArray("chatList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            var roomNo = json.getString("roomNo")
                            var userId = json.getString("userId")
                            var message = json.getString("message")
                            var image = json.getString("image")
                            var messageDate = json.getString("messageDate")

                            countList.add(talklinedata(1,userId,message))
                        }

                        chatlistRecycle.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = talkAdapter(countList,this@chatlistActivity)
                        chatlistRecycle.adapter = adapter

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

