package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class notificationActivity : AppCompatActivity() {
    lateinit var listlisrt: MutableList<notificationDate>
    override fun onCreate(savedInstanceState: Bundle?) {
        val client = OkHttpClient()
        val myApp = myApplication.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification2)

        url()
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        swipeRefreshLayout.setOnRefreshListener {
            url()
        }

        val notificationRecycle = findViewById<RecyclerView>(R.id.notificationRecycle)

        swipeRefreshLayout.viewTreeObserver.addOnScrollChangedListener(
            object : ViewTreeObserver.OnScrollChangedListener {
                /// notificationRecycleの一番上でスクロールされた時のみ、SwipeRefreshを有効にする。
                override fun onScrollChanged() {
                    if (notificationRecycle.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true)
                    else
                        swipeRefreshLayout.setEnabled(false)
                }
            }
        )


    }

    fun url(){

        val client = OkHttpClient()
        val myApp = myApplication.getInstance()
        val notificationRecycle = findViewById<RecyclerView>(R.id.notificationRecycle)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        val ImageVi = findViewById<ImageView>(R.id.noimage)
        val notuuti = findViewById<TextView>(R.id.notuuti)
        var apiUrl = myApp.apiUrl+"notifyList.php?userId="+myApp.loginMyId
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"

        ImageVi.setVisibility(View.GONE)
        notuuti.setVisibility(View.GONE)
        Log.v("blockurl",apiUrl)
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
                        //ぐるぐる消す
                        swipeRefreshLayout.isRefreshing = false
                    }
                }else if(resultError.getString("result") == "success"){
                    this@notificationActivity.runOnUiThread {
                        //ぐるぐる消す
                        swipeRefreshLayout.isRefreshing = false
                        Log.v("blockurl",apiUrl)
                        val date =resultError.getJSONArray("notifyList")
                        listlisrt = mutableListOf<notificationDate>()
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            Log.v("kakunin",i.toString())
                            var json = date.getJSONObject(i)
                            var notifyContent = json.getString("notifyContent")
                            var notifyDate = json.getString("notifyDate")
                            Log.v("blockinfo","notifyContent:" + notifyContent+"notifyDate"+notifyDate)
                            listlisrt.add(notificationDate(1,notifyContent))
                        }
//                        リストを逆にする
                        val result = listlisrt.asReversed()
                        notificationRecycle.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = notificationAdapter(result,this@notificationActivity)
                        notificationRecycle.adapter = adapter

                        //リストが空白なら画像表示
                        Log.v("sizewatch",listlisrt.size.toString())
                        if(listlisrt.size == 0){
                            //表示
                            ImageVi.setVisibility(View.VISIBLE)
                            notuuti.setVisibility(View.VISIBLE)
                        }else{
                            //非表示(領域も埋める)
                            ImageVi.setVisibility(View.GONE)
                            notuuti.setVisibility(View.GONE)
                        }

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