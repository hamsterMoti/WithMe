package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import okhttp3.OkHttpClient

class passwordActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val repasswordEdit = findViewById<EditText>(R.id.repasswordEdit)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val emptyError = myApp.emptyError

        nextButton.setOnClickListener {
            if (passwordEdit.text.toString().isEmpty()) {
                passwordEdit.error = emptyError
            } else if (repasswordEdit.text.toString().isEmpty()) {
                repasswordEdit.error = emptyError
            } else {
                val intent = Intent(this, termsServiceActivity::class.java)
                startActivity(intent)
            }
        }

    }
}