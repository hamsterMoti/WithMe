package com.example.withme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class detailtimelineActivity : AppCompatActivity() {

    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    var postNo = ""
    var userId = ""
    val errormsg = errorApplication.getInstance()


    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: commentAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var commentCntText:TextView
    private lateinit var viewAllText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtimeline)

        supportActionBar //バーを隠す
            ?.hide()
        val titleText = findViewById<TextView>(R.id.title)
        messageRecyclerView = findViewById(R.id.chatRecycle1)
        messageBox = findViewById(R.id.profileEdit)
//        sendButton = findViewById(R.id.sendImage)
        messageList = ArrayList() //配列を初期化
        messageAdapter = commentAdapter(this,messageList)
        commentCntText = findViewById<TextView>(R.id.commentCnt)
        viewAllText = findViewById<TextView>(R.id.viewAllText)

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
//        val commentCntText = findViewById<TextView>(R.id.commentCnt)
        val viewAllText = findViewById<TextView>(R.id.viewAllText)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val scrollView2 = findViewById<ScrollView>(R.id.scrollView2)


//        val profileEdit = findViewById<TextView>(R.id.profileEdit)
        val DMButton = findViewById<Button>(R.id.DMButton)
        val oubobutton = findViewById<Button>(R.id.oubobutton)
        val backButton = findViewById<ImageView>(R.id.backButton)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)

        //val loginuserId = myApp.loginMyId
        var postNo = intent.getStringExtra("postNo")
        var recFlag = intent.getStringExtra("recFlag")
//        var postNo = "6"
        var loginuserId = myApp.loginMyId


        //timelineRecycleの一番上でスクロールされた時のみ、SwipeRefreshを有効にする。
        swipeRefreshLayout.viewTreeObserver.addOnScrollChangedListener(
            object : ViewTreeObserver.OnScrollChangedListener {
                override fun onScrollChanged() {
                    if (scrollView2.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true)
                    else
                        swipeRefreshLayout.setEnabled(false)
                }
            }
        )

        //データ取得ーーーーー
        val apiUrl = "${myApp.apiUrl}postDetail.php?postNo=$postNo&loginUserId=$loginuserId"
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
         Log.v("apiUrl","投稿詳細"+apiUrl)
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                this@detailtimelineActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT).show()
                }
            }
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)

                if(resultError.getString("result") == "error") {
                    this@detailtimelineActivity.runOnUiThread {
                        val error = resultError.getString("errMsg")
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "success"){
                    this@detailtimelineActivity.runOnUiThread {

                        postNo = resultError.getString("postNo")
                        userId = resultError.getString("userId")
                        val userName = resultError.getString("userName")
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
                        var commentCnt = resultError.getInt("commentCnt")
                        val date = resultError.getJSONArray("postCommentList")
                        Log.v("apiUrl","データ"+postDate)
//                        コメント数を表示
                        val strCommentCnt = commentCnt.toString()
                        commentCntText.text = "コメント数$strCommentCnt"
                        val recyclerview = findViewById<RecyclerView>(R.id.chatRecycle1)
                        if (commentCnt < 1) {
                            recyclerview.visibility = View.INVISIBLE
                        }else if(commentCnt < 2){
                            recyclerview.visibility = View.VISIBLE
                            viewAllText.visibility = View.VISIBLE

                            for (i in 0 until date.length()) {
                                val json = date.getJSONObject(i)
                                var commentDate = json.getString("commentDate")
                                val commenterId = json.getString("commenterId")
                                val commenterName = json.getString("commenterName")
                                val comment = json.getString("comment")
                                messageList.add(Message(comment, commenterId, commenterName))
                            }
                        }else{
                            recyclerview.visibility = View.VISIBLE
                            viewAllText.visibility = View.VISIBLE

                            //データが存在する間listにデータを挿入する
                            for (i in 0 until 2) {
                                val json = date.getJSONObject(i)
                                var commentDate = json.getString("commentDate")
                                val commenterId = json.getString("commenterId")
                                val commenterName = json.getString("commenterName")
                                val comment = json.getString("comment")
                                messageList.add(Message(comment, commenterId, commenterName))

                            }
                        }

                        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = commentAdapter(this@detailtimelineActivity ,messageList)
                        messageRecyclerView.adapter = adapter

                        if(gender == "男"){
                            contributorImage.setImageResource(R.drawable.men)
                        }else{
                            contributorImage.setImageResource(R.drawable.woman)
                        }
                        if(appFlag == "1"){
                            oubobutton.setText("応募済み")
                        }else{
                            oubobutton.setText("応募する")
                        }
                        postDate = postDate.substring(0, 10)
                        postdayText.setText("投稿日："+postDate)
                        titleText.text = title
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
                        if(((lowLmit==null)&&(highLmit==null))||((lowLmit=="0")&&(highLmit=="120"))){
                            nendaiText.setText("年代："+"指定なし")
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
//            Toast.makeText(applicationContext, myApp.checkId, Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, mypageActivity::class.java)
            intent.putExtra("targetId",userId)
            startActivity(intent)
        }

//        ーーーーーーーーー

        //message送信処理ーーーーー
        DMButton.setOnClickListener {
            val message = messageBox.text
            val messageURL =
                "${myApp.apiUrl}commentPost.php?userId=${myApp.loginMyId}&postNo=$postNo&content=$message"
            httpAccess(messageURL)
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
                        // Log.v("blockurl",apiUrl)
                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                this@detailtimelineActivity.runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        errormsg.connectionError,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val csvStr = response.body!!.string()
                                val resultError = JSONObject(csvStr)
                                if (resultError.getString("result") == "error") {
                                    val error = resultError.getString("errMsg")
                                    this@detailtimelineActivity.runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            error,
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
        backButton.setOnClickListener {
            val intent = Intent(this,timelineActivity::class.java)

            startActivity(intent)
        }
//        コメント画面に遷移
        viewAllText.setOnClickListener {
//        intentでpostDetail.phpにアクセスするURLを送る
            val intent = Intent(this,commentActivity::class.java)
            intent.putExtra("URL",apiUrl)
            intent.putExtra("postNo",postNo)
            startActivity(intent)
        }
//        ーーーーーーーーーーーー
    }
    private  fun httpAccess(apiUrl:String){
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)

        messageList = ArrayList()
        messageAdapter = commentAdapter(this,messageList)
        val request = Request.Builder().url(apiUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@detailtimelineActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errormsg.connectionError, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    val error = resultError.getString("errMsg")
                    this@detailtimelineActivity.runOnUiThread {
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (resultError.getString("result") == "success") {
                    this@detailtimelineActivity.runOnUiThread {
                        val commentCnt = resultError.getInt("commentCnt")
                        val data = resultError.getJSONArray("postCommentList")
                        if(commentCnt <= 2){
                            val recyclerview = findViewById<RecyclerView>(R.id.chatRecycle1)
                            recyclerview.visibility = View.VISIBLE
                            viewAllText.visibility = View.VISIBLE
                            for (i in 0 until data.length()) {
                                val json = data.getJSONObject(i)
                                val commenterId = json.getString("commenterId")
                                val commenterName = json.getString("commenterName")
                                val comment = json.getString("comment")
                                messageList.add(Message(comment,commenterId,commenterName))
                            }
                        }else{
                            //データが存在する間listにデータを挿入する
                            for (i in 0 until 2) {
                                val json = data.getJSONObject(i)
                                val commenterId = json.getString("commenterId")
                                val commenterName = json.getString("commenterName")
                                val comment = json.getString("comment")
                                messageList.add(Message(comment,commenterId,commenterName))
                            }
                        }
                        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = commentAdapter(this@detailtimelineActivity ,messageList)
                        messageRecyclerView.adapter = adapter
                        //ぐるぐる消す
                        swipeRefreshLayout.isRefreshing = false
                        val strCommentCnt = commentCnt.toString()
                        commentCntText.text = "コメント数$strCommentCnt"

                    }
                }
            }
        })
        messageBox.setText("")
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