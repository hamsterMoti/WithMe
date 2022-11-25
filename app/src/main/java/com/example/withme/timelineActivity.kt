package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class timelineActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        val timelineRecycl = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val fab: View = findViewById(R.id.fab)
//        絞り込み
        val adjustImage = findViewById<ImageView>(R.id.adjustImage)
//        並び替え
        val tuneImage = findViewById<ImageView>(R.id.tuneImage)

        //FABボタンタップ処理
        fab.setOnClickListener { view ->
            var intent = Intent(applicationContext, postActivity::class.java)
            startActivity(intent)
        }

        //初期タイムライン
        var apiUrl = myApp.apiUrl+"timelineInfo.php?userId="+myApp.loginMyId
        access(apiUrl,timelineRecycl)


        adjustImage.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@timelineActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.adjustbottomseet,
                findViewById(R.id.bottomSheet) as LinearLayout?
            )
            bottomSheetView.findViewById<View>(R.id.newPost).setOnClickListener {
//          新着順に並び替える
                Log.v("newpost","新着順")
                var apiUrl = myApp.apiUrl+"timelineInfo.php?userId="+myApp.loginMyId+"&sSort=1"
                access(apiUrl,timelineRecycl)
            }
            bottomSheetView.findViewById<View>(R.id.oldPost).setOnClickListener {
//          投稿順に並び替える
                Log.v("newpost","投稿順")
                var apiUrl = myApp.apiUrl+"timelineInfo.php?userId="+myApp.loginMyId+"&sSort=2"
                access(apiUrl,timelineRecycl)
            }
            bottomSheetView.findViewById<View>(R.id.deadlinePost).setOnClickListener {
//          締め切り近いに並び替える
                Log.v("newpost","締め切り近い")
                var apiUrl = myApp.apiUrl+"timelineInfo.php?userId="+myApp.loginMyId+"&sSort=3"
                access(apiUrl,timelineRecycl)
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        tuneImage.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(
                this@timelineActivity, R.style.BottomSheetDialogTheme
            )
            val tunebottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.tune_bottom_sheet,
                findViewById(R.id.tune) as LinearLayout?
            )

            bottomSheetDialog.setContentView(tunebottomSheetView)
            bottomSheetDialog.show()
        }
    }

    fun access(apiUrl:String,timelineRecycl:RecyclerView){

        //初期のタイムライン
        val countList = mutableListOf<timelinedata>()
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        Log.v("blockurl",apiUrl)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@timelineActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if(resultError.getString("result") == "error") {
                    this@timelineActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                }else if(resultError.getString("result") == "success"){
                    this@timelineActivity.runOnUiThread {
                        val date =resultError.getJSONArray("postList")
                        //データが存在する間listにデータを挿入する
                        for (i in 0 until date.length()) {
                            var json = date.getJSONObject(i)
                            var postNo = json.getString("postNo")
                            var postDate = json.getString("postDate")
                            var category = json.getString("category")
                            var title = json.getString("title")
                            var content = json.getString("content")
                            var term = json.getString("term")
                            var commentNum = json.getString("commentNum")
                            var applyNum = json.getString("applyNum")
                            var status = json.getString("status")
                            var recFlag = json.getString("recFlag")
                            countList.add(timelinedata("カテゴリー：", category, status.toInt(), title, content, "応募件数"+applyNum,postNo,recFlag))
                        }
                        //recycleview
                        timelineRecycl.layoutManager = LinearLayoutManager(applicationContext)
                        val adapter = timelineAdapter(countList, this@timelineActivity)
                        timelineRecycl.adapter = adapter
                    }
                }
            }
        })


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