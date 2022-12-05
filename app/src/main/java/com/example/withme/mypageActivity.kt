package com.example.withme

import android.content.Intent
import android.media.Image
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

class mypageActivity : AppCompatActivity() {
    val myApp = myApplication.getInstance()
    val client = OkHttpClient()

    var addresId = ""
    var userName = ""
    var profile =""
    var icon = ""
    var age =""
    var gender = ""
    var flag = ""
    var post_postNo =""
    var post_category =""
    var apply_category =""
    var post_image = ""
    var post_title1 = ""
    var post_content = ""
    var post_status = ""
    var apply_postNo = ""
    var apply_image = ""
    var apply_content =""
    var apply_title = ""
    var apply_status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val goodRecycle = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val goodButton = findViewById<ImageView>(R.id.goodButton)
        val postButton = findViewById<ImageView>(R.id.postButton)
        val editprofileButton = findViewById<Button>(R.id.editprofileButton)


       //editprofileボタンタップ時の処理
        editprofileButton.setOnClickListener {
            var intent = Intent(applicationContext, editprofileActivity::class.java)
            startActivity(intent)
        }

        mypageActivitysub(myApp)
    }

    fun mypageActivitysub(myapp:myApplication){

        var loginUserId = myApp.loginMyId
        var userId = intent.getStringExtra("targetId")
        if(userId == null){
            userId = myApp.loginMyId
        }else{
            myapp.checkId = userId.toString()
        }
        var saFlag = 0



        val userImage = findViewById<ImageView>(R.id.userImage)
        val addressText = findViewById<TextView>(R.id.addressText)
        val nameText = findViewById<TextView>(R.id.nameText)
        val ageText = findViewById<TextView>(R.id.ageText)
        val profileText = findViewById<TextView>(R.id.profileText)
        val postButton = findViewById<ImageView>(R.id.postButton)
        val goodButton = findViewById<ImageView>(R.id.goodButton)
        val profileRecyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val editprofileButton = findViewById<Button>(R.id.editprofileButton)
        var postList = mutableListOf<gooddata>()
        var goodList = mutableListOf<gooddata>()


        //データ取得
        var apiUrl = myApp.apiUrl+"userPage.php?userId="+userId+"&loginUserId="+loginUserId
        Log.v("alldata",apiUrl)
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@mypageActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@mypageActivity.runOnUiThread {
                    }
                }else if(resultError.getString("result") == "success"){
                    this@mypageActivity.runOnUiThread {
                        addresId = resultError.getString("userId")
                        userName = resultError.getString("userName")
                        profile = resultError.getString("profile")
                        icon = resultError.getString("icon")
                        age = resultError.getString("age")
                        gender = resultError.getString("gender")
                        flag = resultError.getString("flag")

                        //データ表示
                        if(userId == loginUserId){
                            addressText.setText(addresId)
                            saFlag = 1
                        }else{
                            addressText.setText("")
                            editprofileButton.setVisibility(View.INVISIBLE)
                            saFlag = 2
                        }
                        nameText.setText(userName)
                        profileText.setText(profile)
                        ageText.setText(age)
                        if(gender == "男性"){
                            userImage.setImageResource(R.drawable.men)
                        }else{
                            userImage.setImageResource(R.drawable.woman)
                        }


                        var date =resultError.getJSONArray("postList")
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            post_postNo = json.getString("postNo")
                            post_image = json.getString("image")
                            post_title1 = json.getString("title")
                            post_category = json.getString("category")
                            post_content = json.getString("content")
                            post_status = json.getString("status")
                            postList.add(gooddata(post_category,post_title1,post_content,"投稿一覧",post_status.toInt(),post_postNo,"",saFlag))
                        }
                        date =resultError.getJSONArray("applyList")
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            apply_postNo = json.getString("postNo")
                            apply_image = json.getString("image")
                            apply_content = json.getString("content")
                            apply_title = json.getString("title")
                            apply_category = json.getString("category")
                            apply_status = json.getString("status")
                            var apply_userId = json.getString("userId")
                            Log.v("alldata",apply_postNo)
                            goodList.add(gooddata(apply_category,apply_title,apply_content,"参加一覧",apply_status.toInt(),apply_postNo,apply_userId,saFlag))
                        }
                        //初期リスト
                        profileRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = goodAdapter(postList,this@mypageActivity)
                        profileRecyclerView.adapter = adapter
                    }
                }
            }
        })



        //投稿一覧
        postButton.setOnClickListener {
            profileRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(postList,this@mypageActivity)
            profileRecyclerView.adapter = adapter
        }
        //応募一覧表
        goodButton.setOnClickListener {
            profileRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(goodList,this@mypageActivity)
            profileRecyclerView.adapter = adapter
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