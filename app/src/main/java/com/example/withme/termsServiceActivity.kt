package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class termsServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_service)

        val bodyText2 = findViewById<TextView>(R.id.bodyText2)
        bodyText2.text = resources.getString(R.string.bodyTxt)



    }
}