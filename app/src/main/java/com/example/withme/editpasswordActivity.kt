package com.example.withme

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
//パスワード変更画面
class editpasswordActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editpassword)

        val userIdEdit = findViewById<EditText>(R.id.userIdEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val repasswordEdit = findViewById<EditText>(R.id.repasswordEdit)
        val changeButton = findViewById<Button>(R.id.changeButton)
        val emptyError = errormsg.emptyError


        changeButton.setOnClickListener {
            if(userIdEdit.text.toString().isEmpty()){
                userIdEdit.error = emptyError
            }else if (passwordEdit.text.toString().isEmpty()) {
                passwordEdit.error = emptyError
            } else if (repasswordEdit.text.isEmpty()) {
                repasswordEdit.error = emptyError
            } else if (passwordEdit.text.toString() != repasswordEdit.text.toString()) {
                passwordEdit.error = errormsg.notMatch
                repasswordEdit.error = errormsg.notMatch
            } else {
                val myId = userIdEdit.text.toString()
                val pass = passwordEdit.text.toString()
                val userURL = "${myApp.apiUrl}passUpd.php?userId=$myId&password=$pass"
                httpAccess(userURL)
            }
        }
    }

    //    http通信メソッド
    private fun httpAccess(apiUrl: String) {
        val request = Request.Builder().url(apiUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@editpasswordActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    this@editpasswordActivity.runOnUiThread {
                        val error = resultError.getString("errMsg")
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (resultError.getString("result") == "success") {
                    this@editpasswordActivity.runOnUiThread {
                        val intent = Intent(applicationContext, loginActivity::class.java)
                        val msg  = "パスワード変更しました。"
                        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }
            }
        })
    }
}
