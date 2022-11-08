package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class chatlistActivity  : AppCompatActivity() {
    val myApp = myApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlist)

        chatlistActivitysub(myApp)

    }

    fun chatlistActivitysub(myapp:myApplication){

        val chatlistRecycle = findViewById<RecyclerView>(R.id.chatlistRecycle)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<talklinedata>()
        for (i in 1..10){
            countList.add(talklinedata(1,"トウモロコシ","ポップコーンは美味しいよ"))
        }
        chatlistRecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = talkAdapter(countList)
        chatlistRecycle.adapter = adapter
        //----------------------------------------------------------------------
    }
}

