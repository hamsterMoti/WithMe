package com.example.withme

import android.content.Intent
import android.media.Image
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
import java.security.AccessController.getContext


class timelineActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    var sizecount_new = 0
    var sizecount_old = 0

    var sString = "&sString="
    var sSort = "&sSort="
    var sCategory ="&sCategory="

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
        val nolist = findViewById<TextView>(R.id.textView37)
        val ImageVi = findViewById<ImageView>(R.id.noimage)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        val id = recruitGroup.checkedRadioButtonId
        val timelineRecycl = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val fab: View = findViewById(R.id.fab)

        ImageVi.setVisibility(View.GONE)
        nolist.setVisibility(View.GONE)

//        絞り込み
        val adjustImage = findViewById<ImageView>(R.id.adjustImage)
//        並び替え
        val tuneImage = findViewById<ImageView>(R.id.tuneImage)

        var recruitText = "同伴"
        //ラジオボタン処理
        recruitGroup.setOnCheckedChangeListener { _, checkedId: Int ->
            when (checkedId) {
                R.id.douhanRadioButton -> {
                    recruitText = "同伴"
                    Log.v("flogaa", recruitText)
                    access(timelineRecycl, recruitText,ImageVi)
                }
                R.id.soudanRadioButton -> {
                    recruitText = "相談"
                    Log.v("flogaa", recruitText)
                    access(timelineRecycl, recruitText,ImageVi)
                }
            }
        }

        //FABボタンタップ処理
        fab.setOnClickListener { view ->
            var intent = Intent(applicationContext, postActivity::class.java)
            startActivity(intent)
        }

        //初期タイムライン
        access(timelineRecycl, recruitText,ImageVi)
        //下にひぱって更新
        swipeRefreshLayout.setOnRefreshListener {
            access(timelineRecycl, recruitText,ImageVi)
        }
        //timelineRecycleの一番上でスクロールされた時のみ、SwipeRefreshを有効にする。
        swipeRefreshLayout.viewTreeObserver.addOnScrollChangedListener(
            object : ViewTreeObserver.OnScrollChangedListener {
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
            //bottom処理
            bottomsheet(bottomSheetView,R.id.newPost,bottomSheetDialog,"sSort","3",timelineRecycl,recruitText,ImageVi)
            bottomsheet(bottomSheetView,R.id.oldPost,bottomSheetDialog,"sSort","1",timelineRecycl,recruitText,ImageVi)
            bottomsheet(bottomSheetView,R.id.deadlinePost,bottomSheetDialog,"sSort","2",timelineRecycl,recruitText,ImageVi)

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

            //bottom処理
            bottomsheet(tunebottomSheetView,R.id.textView22,bottomSheetDialog,"sCategory","食べ物",timelineRecycl,recruitText,ImageVi)
            bottomsheet(tunebottomSheetView,R.id.textView23,bottomSheetDialog,"sCategory","イベント",timelineRecycl,recruitText,ImageVi)
            bottomsheet(tunebottomSheetView,R.id.textView24,bottomSheetDialog,"sCategory","エンタメ",timelineRecycl,recruitText,ImageVi)
            bottomsheet(tunebottomSheetView,R.id.textView25,bottomSheetDialog,"sCategory","暮らし",timelineRecycl,recruitText,ImageVi)
            bottomsheet(tunebottomSheetView,R.id.textView31,bottomSheetDialog,"sCategory","",timelineRecycl,recruitText,ImageVi)

            bottomSheetDialog.setContentView(tunebottomSheetView)
            bottomSheetDialog.show()
        }

        //エンターで検索
        editTextTextPersonName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event -> // TODO Auto-generated method stub
            Log.d("onEditorAction", "actionId = " + actionId + " event = " + (event ?: "null"))
            sString = "&sString=" + editTextTextPersonName.text
            access(timelineRecycl, recruitText,ImageVi)
            false
        })
    }

    fun bottomsheet(views:View,viewsd:Int,bottomSheetDialog:BottomSheetDialog,squestion:String,categorynumber:String,timelineRecycl:RecyclerView,recruitText:String,ImageVi:ImageView){
        views.findViewById<View>(viewsd).setOnClickListener {
//          新着順に並び替える
            Log.v("kakunin", "squestion："+squestion+"ーcategorynumber："+categorynumber)
            if(squestion == "sSort"){
                sSort = "&sSort="+categorynumber
            }else if(squestion == "sCategory"){
                sCategory = "&sCategory="+categorynumber
            }
            access(timelineRecycl, recruitText,ImageVi)
            //bottomシートclause
            bottomSheetDialog.dismiss()
        }

    }

//    通信処理
    fun access(timelineRecycl: RecyclerView,rec:String,Imagevi:ImageView) {
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiper_for_webview)
        val DouhanList = mutableListOf<timelinedata>()
        val SoudanList = mutableListOf<timelinedata>()
        var apiUrl = myApp.apiUrl + "search.php?userId=" + myApp.loginMyId+sString+sSort+sCategory
        val request = Request.Builder().url(apiUrl).build()
        val errorText = "エラー"
        Log.v("blockurl", apiUrl)
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
                        var errMsg = resultError.getString("errMsg")
                        Toast.makeText(applicationContext, errMsg, Toast.LENGTH_SHORT).show()
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
                            nolist(SoudanList,Imagevi)

                        }else if(rec == "同伴"){
                            adapter = timelineAdapter(DouhanList, this@timelineActivity)
                            nolist(DouhanList,Imagevi)
                        }
                        timelineRecycl.adapter = adapter

                    }
                }
            }
        })
    }

    fun nolist(listlisrt:MutableList<timelinedata>,Imagevi: ImageView){//リストがない場合の画像
        val nolist = findViewById<TextView>(R.id.textView37)
        //リストが空白なら画像表示
        Log.v("sizewatch",listlisrt.size.toString())
        if(listlisrt.size == 0){
            //表示
            Imagevi.setVisibility(View.VISIBLE)
            nolist.setVisibility(View.VISIBLE)
        }else{
            //非表示(領域も埋める)
            Imagevi.setVisibility(View.GONE)
            nolist.setVisibility(View.GONE)
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