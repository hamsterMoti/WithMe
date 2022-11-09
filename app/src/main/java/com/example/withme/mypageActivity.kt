package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class mypageActivity : AppCompatActivity() {
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
        
        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<gooddata>()

        //moreimage : 0 = 投稿一覧　1 = 参加一覧
        countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ","投稿一覧",1))
        goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = goodAdapter(countList,this@mypageActivity)
        goodRecycle.adapter = adapter
        //----------------------------------------------------------------------

        //参加一覧ボタンが押された時の処理
        goodButton.setOnClickListener {

            //adapterにいれる仮データ（後で変更する）-------------------------------------
            val countList = mutableListOf<gooddata>()

            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ","参加一覧",1))
            goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(countList,this@mypageActivity)
            goodRecycle.adapter = adapter
            //----------------------------------------------------------------------

        }
        //投稿一覧ボタンが押された時の処理
        postButton.setOnClickListener {

            //adapterにいれる仮データ（後で変更する）-------------------------------------
            val countList = mutableListOf<gooddata>()

            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ","投稿一覧",1))
            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ","投稿一覧",1))

            goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(countList,this@mypageActivity)
            goodRecycle.adapter = adapter
            //----------------------------------------------------------------------

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