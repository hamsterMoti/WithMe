package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat

class detailtimelineActivity : AppCompatActivity() {

    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    var postNo = ""
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtimeline)

        val postdayText = findViewById<TextView>(R.id.postdayText)
        val postImage = findViewById<ImageView>(R.id.postImage)
        val titleName = findViewById<TextView>(R.id.titleName)
        val overviewText = findViewById<TextView>(R.id.overviewText)
        val kigen = findViewById<TextView>(R.id.kigen)
        val teiintext = findViewById<TextView>(R.id.teiintext)
        val genderText = findViewById<TextView>(R.id.genderText)
        val nendaiText = findViewById<TextView>(R.id.nendaiText)
        val contributorName = findViewById<TextView>(R.id.contributorName)
        val contributorImage = findViewById<ImageView>(R.id.contributorImage)
        val profileEdit = findViewById<TextView>(R.id.profileEdit)
        val DMButton = findViewById<Button>(R.id.DMButton)
        val oubobutton = findViewById<Button>(R.id.oubobutton)

        //val loginuserId = myApp.loginMyId
        var postNo = intent.getStringExtra("postNo")
        var recFlag = intent.getStringExtra("recFlag")
//        var postNo = "6"
        var loginuserId = myApp.loginMyId

        //データ取得ーーーーー
        var apiUrl = myApp.apiUrl+"postDetail.php?postNo="+postNo+"&loginUserId=" + loginuserId
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
         Log.v("blockurl",apiUrl)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@detailtimelineActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@detailtimelineActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "success"){
                    this@detailtimelineActivity.runOnUiThread {

                        postNo = resultError.getString("postNo")
                        userId = resultError.getString("userId")
                        var userName = resultError.getString("userName")
                        var icon = resultError.getString("icon")
                        var postDate = resultError.getString("postDate")
                        title = resultError.getString("title")
                        var categoryName = resultError.getString("categoryName")
                        var content = resultError.getString("content")
                        var gender = resultError.getString("gender")
                        var term = resultError.getString("term")
                        var capacity = resultError.getString("capacity")
                        var hopeGenger = resultError.getString("hopeGenger")
                        var lowLmit = resultError.getString("lowLmit")
                        var highLmit = resultError.getString("highLmit")
                        var status = resultError.getString("status")
                        var recFlag = resultError.getString("recFlag")
                        var appFlag = resultError.getString("appFlag")
                        val date =resultError.getJSONArray("postCommentList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            var commentNo = json.getString("commentNo")
                            var commentDate = json.getString("commentDate")
                            var commenterId = json.getString("commenterId")
                            var commenterIcon = json.getString("commenterIcon")
                            var comment = json.getString("comment")
                        }
                        if(gender == "男性"){
                            contributorImage.setImageResource(R.drawable.men)
                        }else{
                            contributorImage.setImageResource(R.drawable.woman)
                        }
                        if(appFlag == "1"){
                            oubobutton.setText("応募済み")
                        }else{
                            oubobutton.setText("応募する")
                        }
                        postDate = term.substring(0, 10)
                        postdayText.setText("投稿日："+postDate)
                        titleName.setText(title)
                        overviewText.setText(content)
                        term = term.substring(0, 10)
                        kigen.setText(term)

                        if(capacity=="null"){
                            teiintext.setText("定員："+"指定なし")
                        }else{
                            teiintext.setText("定員："+capacity)
                        }
                        if(hopeGenger.isEmpty()){
                            genderText.setText("性別："+"指定なし")
                        }else{
                            genderText.setText("性別："+hopeGenger)
                        }
                        if((lowLmit==null)&&(highLmit==null)){
                            nendaiText.setText("年代："+"指定なし"+"～"+"指定なし")
                        }else{
                            nendaiText.setText("年代："+lowLmit+"～"+highLmit)
                        }
                        contributorName.setText(userName)
                        //ボタン非表示
                        if(recFlag == "相談"){
                            oubobutton.setVisibility(View.INVISIBLE)
                        }
                        if(loginuserId==userId){
                            oubobutton.setText("応募一覧")
                        }
                        if(categoryName == "食べ物"){
                            postImage.setImageResource(R.drawable.user_6)
                        }else if(categoryName == "イベント"){
                            postImage.setImageResource(R.drawable.user_7)
                        } else if(categoryName == "エンタメ"){
                            postImage.setImageResource(R.drawable.user_8)
                        } else if(categoryName == "暮らし"){
                            postImage.setImageResource(R.drawable.user_9)
                        }
                    }
                }
            }
    })
        //マイページへ画面遷移
        contributorImage.setOnClickListener {
            myApp.checkId=userId
            Toast.makeText(applicationContext, myApp.checkId, Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, mypageActivity::class.java)
            intent.putExtra("targetId",userId)
            startActivity(intent)
        }

//        ーーーーーーーーー

        //message送信処理ーーーーー
        DMButton.setOnClickListener {
            var message = profileEdit.text
            var apiUrl =
                myApp.apiUrl + "commentPost.php?userId=" + myApp.loginMyId + "&postNo=" + postNo + "&content=" + message
            val request = Request.Builder().url(apiUrl).build()
            val errorText = "エラー"
             Log.v("blockurl",apiUrl)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    this@detailtimelineActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val csvStr = response.body!!.string()
                    val resultError = JSONObject(csvStr)
                    if (resultError.getString("result") == "error") {
                        this@detailtimelineActivity.runOnUiThread {
                            Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                        }
                    } else if (resultError.getString("result") == "success") {
                        this@detailtimelineActivity.runOnUiThread {
                            Toast.makeText(applicationContext, "成功", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
//        ーーーーーーーーーーーー

//応募ボタン又は応募一覧表示ボタン処理ーーーーーー
        oubobutton.setOnClickListener {
                if (loginuserId == userId) {//投稿一覧画面へ遷移
                    var intent = Intent(applicationContext, applicantListActivity::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("postNo", postNo)
                    startActivity(intent)
                } else {//応募処理
                    if (oubobutton.text == "応募する") {
                        var apiUrl =
                            myApp.apiUrl + "/applyCtl.php?loginUserId=" + myApp.loginMyId + "&postNo=" + postNo + "&appFlag=null"
                        val request = Request.Builder().url(apiUrl).build()
                        val errorText = "エラー"
                        // Log.v("blockurl",apiUrl)
                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                this@detailtimelineActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        errorText,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val csvStr = response.body!!.string()
                                val resultError = JSONObject(csvStr)
                                if (resultError.getString("result") == "error") {
                                    this@detailtimelineActivity.runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            errorText,
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                } else if (resultError.getString("result") == "success") {
                                    this@detailtimelineActivity.runOnUiThread {
                                        oubobutton.setText("応募済み")
                                    }
                                }
                            }
                        })
                    }
                }

        }
//        ーーーーーーーーーーーー
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