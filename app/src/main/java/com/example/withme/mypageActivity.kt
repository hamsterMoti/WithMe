package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<gooddata>()

        countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1,1))
        goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = goodAdapter(countList,this@mypageActivity)
        goodRecycle.adapter = adapter
        //----------------------------------------------------------------------

        //イイねボタンが押された時の処理
        goodButton.setOnClickListener {

            //adapterにいれる仮データ（後で変更する）-------------------------------------
            val countList = mutableListOf<gooddata>()

            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1,1))
            goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(countList,this@mypageActivity)
            goodRecycle.adapter = adapter
            //----------------------------------------------------------------------

        }
        //投稿一覧ボタンが押された時の処理
        postButton.setOnClickListener {

            //adapterにいれる仮データ（後で変更する）-------------------------------------
            val countList = mutableListOf<gooddata>()

            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1,1))
            countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1,1))

            goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = goodAdapter(countList,this@mypageActivity)
            goodRecycle.adapter = adapter
            //----------------------------------------------------------------------

        }


    }
}