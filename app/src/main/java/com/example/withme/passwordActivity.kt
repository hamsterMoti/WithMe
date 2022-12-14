package com.example.withme

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class passwordActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val repasswordEdit = findViewById<EditText>(R.id.repasswordEdit)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val emptyError = errormsg.emptyError
        val myId = intent.getStringExtra("userId")
        val nickname = intent.getStringExtra("userName")
        var birthday = intent.getStringExtra("birthday")
        val gender = intent.getStringExtra("gender")

//        val formatter = DateTimeFormatter.ofPattern("yyyy/[]M/[]d")
//        birthday = LocalDate.parse(birthday, formatter).toString()
//        Log.v("date",birthday.toString())
        nextButton.setOnClickListener {
            if (passwordEdit.text.toString().isEmpty()) {
                passwordEdit.error = emptyError
            } else if (repasswordEdit.text.toString().isEmpty()) {
                repasswordEdit.error = emptyError
            }else if(passwordEdit.text.toString() != repasswordEdit.text.toString() ){
                passwordEdit.error = errormsg.notMatch
                repasswordEdit.error = errormsg.notMatch
            } else {
                if(myId != null) {
                    val pass = passwordEdit.text.toString()
                    val intent = Intent(this, termsServiceActivity::class.java)
                    val UserURL =
                        myApp.apiUrl + "userAdd.php?userId=$myId&userName=$nickname&password=$pass&birthday=$birthday&gender=$gender"
                    intent.putExtra("UserURL", UserURL)
                    myApp.loginMyId = myId
                    startActivity(intent)
                }
            }
        }
    }
}