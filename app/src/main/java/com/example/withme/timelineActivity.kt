package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class timelineActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    var sizecount_new = 0
    var sizecount_old = 0
    var sString  =""

    private var inputMethodManager: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

//        supportActionBar //バーを隠す
//            ?.hide()

//        RadioButon
        val recruitGroup = findViewById<RadioGroup>(R.id.recruitRadioGroup)
        val douhanRadio = findViewById<RadioButton>(R.id.douhanRadioButton)
        val soudanRadioButton = findViewById<RadioButton>(R.id.soudanRadioButton)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        val id = recruitGroup.checkedRadioButtonId

        val timelineRecycl = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val searchbutton = findViewById<Button>(R.id.searchbutton)

        val fab: View = findViewById(R.id.fab)
//        絞り込み
        val adjustImage = findViewById<ImageView>(R.id.adjustImage)
//        並び替え
        val tuneImage = findViewById<ImageView>(R.id.tuneImage)


        var apiUrl = myApp.apiUrl + "search.php?userId=" + myApp.loginMyId

        var recruitText = "同伴"

        //ラジオボタン処理
        recruitGroup.setOnCheckedChangeListener { _, checkedId: Int ->
            when (checkedId) {
                R.id.douhanRadioButton -> {
                    recruitText = "同伴"
                    Log.v("flogaa", recruitText)
                    access(apiUrl, timelineRecycl, recruitText)
                }
                R.id.soudanRadioButton -> {
                    recruitText = "相談"
                    Log.v("flogaa", recruitText)
                    access(apiUrl, timelineRecycl, recruitText)
                }
            }
        }


        //FABボタンタップ処理
        fab.setOnClickListener { view ->
            var intent = Intent(applicationContext, postActivity::class.java)
            startActivity(intent)
        }
        searchbutton.setOnClickListener {
            var apiUrl =
                myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sString=" + editTextTextPersonName.text
            access(apiUrl, timelineRecycl, recruitText)
        }


        //初期タイムライン
        access(apiUrl, timelineRecycl, recruitText)
        //下にひぱって更新
        swipeRefreshLayout.setOnRefreshListener {
            access(apiUrl, timelineRecycl, recruitText)
        }
        swipeRefreshLayout.viewTreeObserver.addOnScrollChangedListener(
            object : ViewTreeObserver.OnScrollChangedListener {
                /// notificationRecycleの一番上でスクロールされた時のみ、SwipeRefreshを有効にする。
                override fun onScrollChanged() {
                    if (timelineRecycl.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true)
                    else
                        swipeRefreshLayout.setEnabled(false)
                }
            }
        )


//        RadioButton(相談)
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
                Log.v("newpost", "新着順")
                var apiUrl = myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sSort=1"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            bottomSheetView.findViewById<View>(R.id.oldPost).setOnClickListener {
//          投稿順に並び替える
                Log.v("newpost", "投稿順")
                var apiUrl = myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sSort=2"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            bottomSheetView.findViewById<View>(R.id.deadlinePost).setOnClickListener {
//          締め切り近いに並び替える
                Log.v("newpost", "締め切り近い")
                var apiUrl = myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sSort=3"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
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

            tunebottomSheetView.findViewById<View>(R.id.textView22).setOnClickListener {
//          絞り込みー料理グルメ
                Log.v("newpost", "食べ物")
                var apiUrl =
                    myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sCategory=食べ物"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            tunebottomSheetView.findViewById<View>(R.id.textView23).setOnClickListener {
//          絞り込みーお酒
                Log.v("newpost", "イベント")
                var apiUrl =
                    myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sCategory=イベント"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            tunebottomSheetView.findViewById<View>(R.id.textView24).setOnClickListener {
//          絞り込みースポーツ
                Log.v("newpost", "エンタメ")
                var apiUrl =
                    myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sCategory=エンタメ"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            tunebottomSheetView.findViewById<View>(R.id.textView25).setOnClickListener {
//          絞り込みーゲーム
                Log.v("newpost", "暮らし")
                var apiUrl =
                    myApp.apiUrl + "search.php?userId=" + myApp.loginMyId + "&sCategory=暮らし"
                access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            tunebottomSheetView.findViewById<View>(R.id.textView31).setOnClickListener {
//          絞り込みーゲーム
                Log.v("newpost", "絞り込み無")
                var apiUrl =
                    myApp.apiUrl + "search.php?userId=" + myApp.loginMyId +
                            access(apiUrl, timelineRecycl, recruitText)
                //bottomシートclause
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(tunebottomSheetView)
            bottomSheetDialog.show()
        }

        //エンターで検索
        editTextTextPersonName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event -> // TODO Auto-generated method stub
            Log.d("onEditorAction", "actionId = " + actionId + " event = " + (event ?: "null"))
            sString = "&sString=" + editTextTextPersonName.text
            access(apiUrl, timelineRecycl, recruitText)
            false
        })

    }

    fun access(apiUrl: String, timelineRecycl: RecyclerView,rec:String) {
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        //初期のタイムライン
        val DouhanList = mutableListOf<timelinedata>()
        val SoudanList = mutableListOf<timelinedata>()
        val request = Request.Builder().url(apiUrl+sString).build()
        val errorText = "エラー"
        Log.v("blockurl", apiUrl+sString)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@timelineActivity.runOnUiThread {
                    Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val csvStr = response.body!!.string()
                val resultError = JSONObject(csvStr)
                if (resultError.getString("result") == "error") {
                    this@timelineActivity.runOnUiThread {
                        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                            .show()
                        //ぐるぐる消す
                        swipeRefreshLayout.isRefreshing = false
                    }
                } else if (resultError.getString("result") == "success") {
                    this@timelineActivity.runOnUiThread {
                        val date = resultError.getJSONArray("postList")
                        //ぐるぐる消す
                        swipeRefreshLayout.isRefreshing = false
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

                            term = term.substring(0, 10)

                            if(recFlag == "同伴"){
                                DouhanList.add(timelinedata("カテゴリー：", category, status.toInt(), title, content, commentNum, applyNum, postNo, recFlag, term ))
                            }else if (recFlag == "相談"){
                                SoudanList.add(timelinedata("カテゴリー：", category, status.toInt(), title, content, commentNum, applyNum, postNo, recFlag, term ))
                            }

                        }
                        //recycleview
                        timelineRecycl.layoutManager = LinearLayoutManager(applicationContext)
                        var adapter = timelineAdapter(SoudanList, this@timelineActivity)
                        if(rec == "相談"){
                            adapter = timelineAdapter(SoudanList, this@timelineActivity)
                        }else if(rec == "同伴"){
                            adapter = timelineAdapter(DouhanList, this@timelineActivity)
                        }
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