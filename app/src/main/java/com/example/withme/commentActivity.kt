package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class commentActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        supportActionBar //バーを隠す
            ?.hide()

        messageRecyclerView = findViewById(R.id.commentRecycle)
        messageBox = findViewById(R.id.commentEditText)
        sendButton = findViewById(R.id.sendImage)
        messageList = ArrayList() //配列を初期化
        messageAdapter = MessageAdapter(this,messageList)

        val titleText = findViewById<TextView>(R.id.titleText)
        val backButton = findViewById<ImageView>(R.id.backButton)


        //intentでpostDetail.phpにアクセスするURLを取得
        val commentAccessURL = intent.getStringExtra("URL")
        val postNo = intent.getStringExtra("postNo")
        val userId = myApp.loginMyId

        // 画面を開いた瞬間にhttp接続開始
        if (commentAccessURL != null) {
            httpAccess(commentAccessURL)
            Log.v("commentAccessURL", commentAccessURL)


            //送信処理
            sendButton.setOnClickListener {
                val message = messageBox.text.toString()
                val messageURL =
                    "${myApp.apiUrl}commentPost.php?userId=$userId&postNo=$postNo&content=$message"

                Log.v("送信メッセージ", messageURL)
                //http通信開始
                httpAccessPost(messageURL)
                httpAccess(commentAccessURL)
            }
            //戻るボタン
            backButton.setOnClickListener {
                val intent = Intent(applicationContext, detailtimelineActivity::class.java)
                intent.putExtra("postNo", postNo)
                startActivity(intent)
            }
        }

    }
    //    コメントを表示
    private  fun httpAccess(apiUrl:String){
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        val request = Request.Builder().url(apiUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@commentActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    val error = resultError.getString("errMsg")
                    this@commentActivity.runOnUiThread {
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (resultError.getString("result") == "success") {
                    this@commentActivity.runOnUiThread {
                        val data = resultError.getJSONArray("postCommentList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until data.length()) {
                            val json = data.getJSONObject(i)
                            val commenterId = json.getString("commenterId")
                            val commenterName = json.getString("commenterName")
                            val comment = json.getString("comment")
                            messageList.add(Message(comment,commenterId,commenterName))
                        }

                        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = MessageAdapter(this@commentActivity ,messageList)
                        messageRecyclerView.adapter = adapter

                    }
                }
            }
        })
    }
    //    メッセージ送信
    private  fun httpAccessPost(apiUrl:String){
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        val request = Request.Builder().url(apiUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@commentActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    val error = resultError.getString("errMsg")
                    this@commentActivity.runOnUiThread {
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (resultError.getString("result") == "success") {
                    this@commentActivity.runOnUiThread {

                    }
                }
            }
        })
        messageBox.setText("")
    }
}