package com.example.withme

//import sun.security.krb5.Confounder.bytes
//import jdk.nashorn.internal.objects.NativeRegExp.source
//awsS3で使用


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import okhttp3.*
import org.json.JSONObject
import java.io.*
import java.util.*


class postActivity : AppCompatActivity() {

    private lateinit var file: File
    private lateinit var imageFile: File
    private lateinit var uri: Uri
    //    var uri: Uri? = null
    val client = OkHttpClient()
    private val REQUEST_GALLERY_TAKE = 2
    private val TEMP_FILE_NAME = "temp_upload_image"
    private lateinit var recruitDaySp: TextView
    private lateinit var bitmap: Bitmap
    private lateinit var jpgarr: ByteArray
    private  lateinit var b64Encode:String
    var ye = ""
    var mon = ""
    var day = ""
    var test = arrayListOf("","0","120","")
    val myApp = myApplication.getInstance()
    var cal: Calendar = Calendar.getInstance()
    val yyyy = cal.get(Calendar.YEAR);
    val mm = cal.get(Calendar.MONTH)
    val dd = cal.get(Calendar.DAY_OF_MONTH);
    val errormsg = errorApplication.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        postsub(myApp)

    }


    fun postsub(myapp:myApplication){

        val emptyError = errormsg.emptyError
        val overError = errormsg.overError
        recruitDaySp = findViewById<TextView>(R.id.recruitDaySp)
        val titleEdit = findViewById<EditText>(R.id.titleEdit)
        val categrySp = findViewById<Spinner>(R.id.categrySp)
        val syuruiSp = findViewById<Spinner>(R.id.syuruiSp)
        val contentEdit = findViewById<EditText>(R.id.contentEdit)
        val postButton = findViewById<Button>(R.id.postButton)
        var ragiaG = findViewById<RadioGroup>(R.id.radioG)
        val ariButton = findViewById<RadioButton>(R.id.radioButton)
        val nasiButton = findViewById<RadioButton>(R.id.radioButton3)
        val sgender = findViewById<TextView>(R.id.sgender)
        val sage = findViewById<TextView>(R.id.sage)
        val titlecount = findViewById<TextView>(R.id.titlecount)
        val textView41= findViewById<TextView>(R.id.textView41)
        val count = findViewById<TextView>(R.id.textView41)
        val stain = findViewById<TextView>(R.id.stain)
        sgender.setVisibility(View.GONE)
        sage.setVisibility(View.GONE)
        stain.setVisibility(View.GONE)
        var flag = 0
        var flag2 = 0
        var flag3 = 0
        var flag4 = 0

        //title文字カウント
        titleEdit.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var textColor = Color.GRAY
                Log.v("textcount",p0?.length.toString())
                var txtLength = p0?.length
                titlecount.setText(txtLength.toString()+"/15")
                if((p0?.length!! >= 16)||(p0?.length!! ==0 )){
                    textColor = Color.RED
                }
                titlecount.setTextColor(textColor)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //title文字カウント
        contentEdit.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var textColor = Color.GRAY
                Log.v("textcount",p0?.length.toString())
                var txtLength = p0?.length
                count.setText(txtLength.toString()+"/1000")
                if((p0?.length!! >= 1001)||(txtLength == 0)){
                    textColor = Color.RED
                }
                textView41.setTextColor(textColor)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })



        //ラジオボタン処理
        ragiaG.setOnCheckedChangeListener { _, checkedId: Int ->
            when (checkedId) {
                R.id.radioButton3 -> {//なし
                    test = arrayListOf("","0","120","")
                    sgender.setVisibility(View.GONE)
                    sage.setVisibility(View.GONE)
                    stain.setVisibility(View.GONE)
                }
            }
        }

        //スピナー登録
        val categoryarray = arrayOf("食べ物", "イベント","エンタメ","暮らし")
        val syuruiarray = arrayOf( "同伴", "相談")
        val categoryarrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryarray)
        val syuruiarrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, syuruiarray)
        categrySp.adapter = categoryarrayAdapter
        syuruiSp.adapter = syuruiarrayAdapter

        //募集日時textタップ処理
        recruitDaySp.setOnClickListener{
            showDatePicker()
        }

        postButton.setOnClickListener {

            if((titleEdit.text.toString().isEmpty())||(contentEdit.text.toString().isEmpty())||(titleEdit.text.length >= 16)||(recruitDaySp.text.toString()=="タップしてください")) {
                if ((titleEdit.text.toString().isEmpty())) {
                    titleEdit.error = emptyError
                }
                if (contentEdit.text.toString().isEmpty()) {
                    contentEdit.error = emptyError
                }
                if (titleEdit.text.length >= 16) {
                    titleEdit.error = overError
                }
                if (recruitDaySp.text.toString() == "タップしてください") {
                    Toast.makeText(this, "募集期間を入力してください", Toast.LENGTH_LONG).show()
                }
                flag = 0
            }else{
                flag = 1
            }

            if(flag == 1) {
//             　選択されているアイテム取得
                val categrySpitem = categrySp.selectedItem.toString()
                val syuruiSpitem = syuruiSp.selectedItem.toString()

//          送るようにdata型に変更する
                if (mon.length == 1) { mon = "0" + mon }
                if (day.length == 1) { day = "0" + day }
                val bosyudata = ye + "-" + mon + "-" + day

//          条件選択が指定なし簿場合空白にする
                for (cnt in 1..3) {
                    if (test[cnt] == "指定なし") {
                        test[cnt] = ""
                    }
                }

//            入力データ確認
//            var test = arrayListOf("性別","開始年代","終了年代","定員")
                Log.v(
                    "alldata",
                    "タイトル-" + titleEdit.text.toString() + "種類-" + syuruiSpitem + "カテゴリ-" + categrySpitem + "日時-" +
                            bosyudata + "内容-" + contentEdit.text.toString() + "性別-" + test[0] + "募集年代-" + test[1] + "~" + test[2] + "定員-" + test[3]
                )

                if (test[1].isEmpty()) {
                    test[1] = "0"
                }
                if (test[2].isEmpty()) {
                    test[2] = "120"
                }
                var apiUrl =
                    myApp.apiUrl + "postAdd.php?userId=" + myapp.loginMyId + "&category=" + categrySpitem + "&title=" + titleEdit.text + "&content=" + contentEdit.text + "&term=" + bosyudata + "&capacity=" + test[3] + "&hopeGender=" + test[0] + "&lowLimit=" + test[1] + "&highLimit=" + test[2] + "&recFlag=" + syuruiSpitem

                if (test[3].isEmpty()) {
                    apiUrl =
                        myApp.apiUrl + "postAdd.php?userId=" + myapp.loginMyId + "&category=" + categrySpitem + "&title=" + titleEdit.text + "&content=" + contentEdit.text + "&term=" + bosyudata + "&hopeGender=" + test[0] + "&lowLimit=" + test[1] + "&highLimit=" + test[2] + "&recFlag=" + syuruiSpitem
                }

                Log.v("確認", apiUrl)
                Log.v("alldata", apiUrl)
                Log.v("alldata", categrySpitem)
                val request = Request.Builder().url(apiUrl).build()
                val errorText = "エラー"
                Log.v("blockurl", apiUrl.toString())
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        this@postActivity.runOnUiThread {
                            Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val csvStr = response.body!!.string()
                        val resultError = JSONObject(csvStr)
                        if (resultError.getString("result") == "error") {
                            this@postActivity.runOnUiThread {
                            }
                        } else if (resultError.getString("result") == "success") {
                            this@postActivity.runOnUiThread {
                                Toast.makeText(applicationContext, "投稿しました", Toast.LENGTH_SHORT) .show()
                                //マイページ画面へ遷移
                                var intent =
                                    Intent(applicationContext, timelineActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                })
            }
        }
    }

    //ダイアログ
    fun showDialog(view: View?) {
        val dialogFragment: DialogFragment = myDialogFragment(test,this@postActivity)
        dialogFragment.show(supportFragmentManager, "my_dialog")
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                recruitDaySp.text = "${year}/${month + 1}/${dayOfMonth}"
                ye = "${year}"
                mon ="${month+1}"
                day = "${dayOfMonth}"
            },
            yyyy, mm, dd)
        datePickerDialog.show()
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