package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class notificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification2)

        val notificationRecycle = findViewById<RecyclerView>(R.id.recyclerView)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<notificationDate>()

        countList.add(notificationDate(1,"talkが来たよ"))
        countList.add(notificationDate(1,"talkがきたよ",))

        notificationRecycle.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = notificationAdapter(countList)
        notificationRecycle.adapter = adapter
        //----------------------------------------------------------------------

    }
}