package com.example.withme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class editprofileActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    private val REQUEST_GALLERY_TAKE = 2

    private lateinit var userImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val nameEdit = findViewById<EditText>(R.id.nameEdit)
        val profileEdit = findViewById<EditText>(R.id.profileEdit)
        val namelayout = findViewById<TextInputLayout>(R.id.namelayout)
        val profilelayout = findViewById<TextInputLayout>(R.id.profilelayout)
        val saveButton = findViewById<Button>(R.id.saveButton)
        var textCount = findViewById<TextView>(R.id.textCount)
        var count = findViewById<TextView>(R.id.count)
        val emptyError = errormsg.emptyError
        val userName = intent.getStringExtra("userName")
        val profile = intent.getStringExtra("profile")

        nameEdit.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                var textColor = Color.GRAY
                Log.v("textcount",p0?.length.toString())
                var txtLength = p0?.length
                textCount.setText(txtLength.toString()+"/15")
                if((p0?.length!! >= 16)||(p0?.length == 0)){
                    textColor = Color.RED
                    if(p0?.length == 0){
                        nameEdit.hint = ""
                    }
                }else{
                    namelayout.error = null
                }
                textCount.setTextColor(textColor)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        profileEdit.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                var textColor = Color.GRAY
                Log.v("textcount",p0?.length.toString())
                var txtLength = p0?.length
                count.setText(txtLength.toString()+"/66")
                if(p0?.length!! >= 67){
                    textColor = Color.RED
                }else{
                    profilelayout.error = null
                }
                if(p0?.length!! == 0){
                    profileEdit.hint = ""
                }
                count.setTextColor(textColor)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        nameEdit.hint = userName
        nameEdit.setText(userName)

        if(profile == "null"){
            profileEdit.hint = "未設定"
        }else{
            profileEdit.setText(profile)
            profileEdit.hint = profile
        }
        var errormsg = errorApplication.getInstance()

        //データ保存処理
        saveButton.setOnClickListener {
            if (nameEdit.text.toString().isEmpty()) {
                namelayout.error = emptyError
            }else if((nameEdit.text.length >= 16)||(profileEdit.text.length >= 67)) {
                if (nameEdit.text.length >= 16) {
                    namelayout.error = errormsg.overError
                }
                if (profileEdit.text.length >= 67) {
                    profilelayout.error = errormsg.overError
                }
            }else{
                val apiUrl =
                    "${myApp.apiUrl}userUpd.php?userId=${myApp.loginMyId}&userName=${nameEdit.text.toString()}&profile=${profileEdit.text.toString()}"
                val request = Request.Builder().url(apiUrl).build()
//                var countList = mutableListOf<timelinedata>()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        this@editprofileActivity.runOnUiThread {
                            Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val csvStr = response.body!!.string()
                        val resultError = JSONObject(csvStr)
                        if (resultError.getString("result") == "error") {
                            this@editprofileActivity.runOnUiThread {
                                val error = resultError.getString("errMsg")
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else if (resultError.getString("result") == "success") {
                            this@editprofileActivity.runOnUiThread {
                               //(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
                                //マイページ画面へ遷移
                                val intent = Intent(applicationContext, mypageActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                })
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val timeline = Intent(this, timelineActivity::class.java)
        val chat = Intent(this, chatlistActivity::class.java)
        val notification = Intent(this, notificationActivity::class.java)
        val mypage = Intent(this, mypageActivity::class.java)
        val settingsettinglist = Intent(this, settinglistActivity::class.java)
        val login = Intent(this, loginActivity::class.java)

        //連携処理を実施
        when (item.itemId) {
            R.id.timeline -> startActivity(timeline)
            R.id.chat -> startActivity(chat)
            R.id.notification -> startActivity(notification)
            R.id.mypage -> startActivity(mypage)
            R.id.setting -> startActivity(settingsettinglist)
            else -> startActivity(login)//ログアウト処理を書く
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}