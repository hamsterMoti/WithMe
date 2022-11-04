package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class detailtimelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtimeline)

        val oubobutton = findViewById<Button>(R.id.oubobutton)
        //ボタン非表示
        //oubobutton.setVisibility(View.INVISIBLE)


        oubobutton.setOnClickListener {
            var intent = Intent(applicationContext, applicantListActivity::class.java)
            startActivity(intent)
        }

    }
}