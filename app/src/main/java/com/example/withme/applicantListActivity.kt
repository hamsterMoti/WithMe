package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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
        applicantListActivitysub(myApp,postNo.toString())

    }

    fun applicantListActivitysub(myapp:myApplication,postNo:String){

        var applicantListRecyeclerView = findViewById<RecyclerView>(R.id.applicantListRecyeclerView)
        val countList = mutableListOf<applicantDate>()
        var addButton = findViewById<Button>(R.id.groupaddbutton)

        var meaddButton = findViewById<Button>(R.id.addButton)



        //        val loginuserId = myApp.loginMyId
//        var postNo = intent.getStringExtra("postNo")
        var postNo = "6"
        var loginuserId = "2200166@ecc.ac.jp"


        //recycleviewの処理
        var apiUrl = myApp.apiUrl+"applyList.php?postNo="+postNo
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        Log.v("blockurl", apiUrl.toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@applicantListActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@applicantListActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "succes"){
                    this@applicantListActivity.runOnUiThread {
//                        Log.v("blockurl",apiUrl)
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
                        if(roomFlg == 2) {
                            addButton.setText("グループ作成済み")
                        }
                        Thread.sleep(500)
                        applicantListRecyeclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = listapplicantAdapter(countList,this@applicantListActivity)
                        applicantListRecyeclerView.adapter = adapter

                    }
                }
            }
        })

        //グループ作成処理
        addButton.setOnClickListener {
            if(roomFlg==2){
                Toast.makeText(applicationContext, "作成済みです", Toast.LENGTH_SHORT)
                    .show()
            }else{
                //グループ作成の処理
                var apiUrl = myApp.apiUrl+"roomCreate.php?postNo="+postNo+"&userId="+loginuserId
                val request = Request.Builder().url(apiUrl).build()
                val errorText = "エラー"
                Log.v("blockurl", apiUrl.toString())
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        this@applicantListActivity.runOnUiThread {
                            Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val csvStr = response.body!!.string()
                        val resultError = JSONObject(csvStr)
                        if(resultError.getString("result") == "error") {
                            this@applicantListActivity.runOnUiThread {
                                Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }else if(resultError.getString("result") == "succes"){
                            this@applicantListActivity.runOnUiThread {
                                Toast.makeText(applicationContext, "成功", Toast.LENGTH_SHORT)
                                    .show()
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