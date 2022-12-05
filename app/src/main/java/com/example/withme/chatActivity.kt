package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Array.getInt

class chatActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    private lateinit var messageRecyclerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>

    var receiverRoom:String? = null
    var sendRoom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar //バーを隠す
            ?.hide()
//        intentでroomNoを受け取る
//        val roomNo = intent.getStringExtra("UserURL")
        val roomNo = 22
//        val userId = getInt("userId")
        val userId = "2200166@ecc.ac.jp"
        messageRecyclerView = findViewById(R.id.chatRecycle)
        messageBox = findViewById(R.id.chatEditText)
        sendButton = findViewById(R.id.sendImage)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

//        チャット更新
        val URL = myApp.apiUrl+"chat.php?roomNo=$roomNo"
        // http接続開始
        val request = Request.Builder().url(URL).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@chatActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    this@chatActivity.runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            errormsg.notMatch,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else if (resultError.getString("result") == "success") {
                    this@chatActivity.runOnUiThread {
                        val date =resultError.getJSONArray("chatList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            val json = date.getJSONObject(i)
                            val myId = json.getString("userId")
                            val userName = json.getString("userName")
                            var icon = json.getString("icon")
                            val message = json.getString("message")
                            var image = json.getString("image")
                            var messageDate = json.getString("messageDate")

                            messageList.add(Message(message,userId))
                        }

                        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = MessageAdapter(this@chatActivity ,messageList)
                        messageRecyclerView.adapter = adapter

                    }
                }
            }
        })
//        仮データ
        val senderUid = "000"
//        送信ボタン
        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
//            URLのパラメータ
//            userId+roomNo+message
            val messageURL = myApp.apiUrl+"chatPost.php?userId=$userId&roomNo=$roomNo&message=$message"
            Log.v("message",messageURL)
            // http接続開始
            val request = Request.Builder().url(messageURL).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    this@chatActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val csvStr = response.body!!.string()
                    val resultError = JSONObject(csvStr)
                    if (resultError.getString("result") == "error") {
                        this@chatActivity.runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                errormsg.notMatch,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else if (resultError.getString("result") == "success") {
                        this@chatActivity.runOnUiThread {
                            val date =resultError.getJSONArray("chatList")
                            //データが存在する間listにデータを挿入する
                            for (i in 0 until date.length()) {
                                val json = date.getJSONObject(i)
                                val myId = json.getString("userId")
                                val userName = json.getString("userName")
                                var icon = json.getString("icon")
                                val message = json.getString("message")
                                var image = json.getString("image")
                                var messageDate = json.getString("messageDate")

                                messageList.add(Message(message,userId))
                            }

                            messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            val adapter = MessageAdapter(this@chatActivity ,messageList)
                            messageRecyclerView.adapter = adapter

                        }
                    }
                }
            })


        }
//        戻るボタン


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