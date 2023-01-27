package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class applicantListActivity : AppCompatActivity() {
    val myApp = myApplication.getInstance()
    val client = OkHttpClient()
    var roomFlg = 0 //1 =未作成　２＝作成済
    var addFlg = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_list)

        var titleText = findViewById<TextView>(R.id.textView14)

        //投稿詳細画面からのインテント取得
        var postNo = intent.getStringExtra("postNo")
        var title = intent.getStringExtra("title")

        titleText.setText("~"+title.toString()+"~")

        Log.v("postNo",postNo.toString())
        Log.v("title",title.toString())
        applicantListActivitysub(myApp,postNo.toString())//通信処理

    }

    fun applicantListActivitysub(myapp:myApplication,postNo:String){

        var applicantListRecyeclerView = findViewById<RecyclerView>(R.id.applicantListRecyeclerView)
        var nolistImage = findViewById<ImageView>(R.id.imageView18)
        var nolistText = findViewById<TextView>(R.id.textView39)
        val countList = mutableListOf<applicantDate>()
        var meaddButton = findViewById<Button>(R.id.addButton)
        var apiUrl = myApp.apiUrl+"applyList.php?postNo="+postNo
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        Log.v("blockurl", apiUrl.toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@applicantListActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                    Log.v("blockurl", "error")
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@applicantListActivity.runOnUiThread {
                        Log.v("blockurl", "error1")
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "succes"){
                    this@applicantListActivity.runOnUiThread {
                        roomFlg = resultError.getInt("roomFlg")
                        val date =resultError.getJSONArray("applyList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            var userName = json.getString("userName")
                            var userId = json.getString("userId")
                            var icon = json.getString("icon")
                            var age = json.getString("age")
                            var gender = json.getString("gender")
                            addFlg = json.getInt("addFlg")
                            Log.v("kakunin","確認"+userName+"roomFlg"+addFlg)
                            countList.add(applicantDate(0,userName,userId,age,gender,addFlg,postNo,roomFlg))
                        }
                        if(countList.size == 0){
                            //表示
                            nolistImage.setVisibility(View.VISIBLE)
                            nolistText.setVisibility(View.VISIBLE)
                        }else{
                            //非表示(領域も埋める)
                            nolistImage.setVisibility(View.GONE)
                            nolistText.setVisibility(View.GONE)
                        }
                        Log.v("blockurl",roomFlg.toString())
                        Thread.sleep(500)
                        applicantListRecyeclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = listapplicantAdapter(countList,this@applicantListActivity)
                        applicantListRecyeclerView.adapter = adapter

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