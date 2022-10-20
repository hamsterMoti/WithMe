package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class timelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        val timelineRecycl = findViewById<RecyclerView>(R.id.recyclerView)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<timelinedata>()
        for (i in 1..10){
            countList.add(timelinedata("カテゴリー：","食べ物",0,"食べ放題行く人","大阪で募集","解答件数"))
        }
        timelineRecycl.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = timelineAdapter(countList)
        timelineRecycl.adapter = adapter
        //----------------------------------------------------------------------



    }
}