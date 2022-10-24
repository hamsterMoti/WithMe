package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class mypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val goodRecycle = findViewById<RecyclerView>(R.id.recyclerView)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<gooddata>()

        countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1))
        countList.add(gooddata(1,"トウモロコシ","ポップコーンは美味しいよ",1,1))

        goodRecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = goodAdapter(countList,this@mypageActivity)
        goodRecycle.adapter = adapter
        //----------------------------------------------------------------------
    }
}