package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class termsServiceActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_service)

        val bodyText2 = findViewById<TextView>(R.id.bodyText2)
        bodyText2.text = resources.getString(R.string.bodyTxt)
        val agreeCheckBox = findViewById<CheckBox>(R.id.agreeCheckBox)
        val nextButton = findViewById<Button>(R.id.nextButton)

//        intentでURLを受け取る
        val userAddURL = intent.getStringExtra("UserURL")
        if (userAddURL != null) {
            Log.v("userAddURL",userAddURL)
        }
        // チェックされたらタイムライン画面に遷移する
        nextButton.setOnClickListener{
            if(agreeCheckBox.isChecked) {
                if (userAddURL != null) {
                    // http接続開始
                    val request = Request.Builder().url(userAddURL).build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            this@termsServiceActivity.runOnUiThread {
                                Toast.makeText(applicationContext, "erorr", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val csvStr = response.body!!.string()
                            val resultError = JSONObject(csvStr)
                            if (resultError.getString("result") == "error") {
                                this@termsServiceActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        errormsg.notMatch,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            } else if (resultError.getString("result") == "success") {
                                this@termsServiceActivity.runOnUiThread {
                                    val intent =
                                        Intent(applicationContext, timelineActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}