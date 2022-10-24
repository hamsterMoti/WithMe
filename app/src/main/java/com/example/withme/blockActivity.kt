package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class blockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        val blockrecycle = findViewById<RecyclerView>(R.id.blockrecycle)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<blockDate>()
        for (i in 1..10){
            countList.add(blockDate(1,"トウモロコシ","トマト嫌い",1))
        }
        blockrecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = blocAdapter(countList,this)
        blockrecycle.adapter = adapter
        //----------------------------------------------------------------------


    }
}