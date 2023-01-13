package com.example.withme

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
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
        val birthday = intent.getStringExtra("birthday")
        val gender = intent.getStringExtra("gender")

        val passInputLayout = findViewById<TextInputLayout>(R.id.password_layout)
        val repassInputLayout = findViewById<TextInputLayout>(R.id.repassword_layout)

        nextButton.setOnClickListener {
            if (passwordEdit.text.toString().isEmpty()) {
                passInputLayout.error = emptyError
                if (repasswordEdit.text.toString().isEmpty()) {
                    repassInputLayout.error = emptyError
                }
            }else if(passwordEdit.text.toString() != repasswordEdit.text.toString() ){
                passInputLayout.error = errormsg.notMatch
                repassInputLayout.error = errormsg.notMatch
            } else {
                passInputLayout.error = null
                repassInputLayout.error = null

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