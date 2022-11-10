package com.example.withme

//import sun.security.krb5.Confounder.bytes
//import jdk.nashorn.internal.objects.NativeRegExp.source
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import okhttp3.OkHttpClient
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class postActivity : AppCompatActivity() {

    private val REQUEST_GALLERY_TAKE = 2
    private lateinit var photo: ImageView
    private lateinit var recruitDaySp: TextView
    private lateinit var bitmap: Bitmap
    private lateinit var jpgarr: ByteArray
    private  lateinit var b64Encode:String
    var ye = ""
    var mon = ""
    var day = ""
    var test = arrayListOf("性別","開始年代","終了年代","定員")
    val myApp = myApplication.getInstance()
    var cal: Calendar = Calendar.getInstance()
    val yyyy = cal.get(Calendar.YEAR);
    val mm = cal.get(Calendar.MONTH)
    val dd = cal.get(Calendar.DAY_OF_MONTH);
    val errormsg = errorApplication.getInstance()
    var client = OkHttpClient()
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        postsub(myApp)

    }


    fun postsub(myapp:myApplication){

        val emptyError = errormsg.emptyError
        photo = findViewById<ImageView>(R.id.photo)
        recruitDaySp = findViewById<TextView>(R.id.recruitDaySp)
        val titleEdit = findViewById<EditText>(R.id.titleEdit)
        val categrySp = findViewById<Spinner>(R.id.categrySp)
        val syuruiSp = findViewById<Spinner>(R.id.syuruiSp)
        val contentEdit = findViewById<EditText>(R.id.contentEdit)
        val postButton = findViewById<Button>(R.id.postButton)
        val ariButton = findViewById<Button>(R.id.ariButton)
        val nasiButton = findViewById<Button>(R.id.nasiButton)
        var flag = 0
        var flag2 = 0
        var flag3 = 0
        var flag4 = 0

        //画像タップ時の処理
        photo.setOnClickListener {
            openGalleryForImage()
        }

        //スピナー登録
        val categoryarray = arrayOf("料理・グルメ", "趣味")
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

//            入力値チェック
            if ((titleEdit.text.toString().isEmpty())) {
                titleEdit.error = emptyError
                flag = 0
            }else{
                flag = 1
            }
            if (contentEdit.text.toString().isEmpty()) {
                contentEdit.error = emptyError
                flag2 = 0
            }else{
                flag2 = 1
            }
            if(recruitDaySp.text.toString()=="タップしてください"){
                Toast.makeText(this , "募集期間を入力してください", Toast.LENGTH_LONG).show();
                flag3 = 0
            }else{
                flag3 = 1
            }

            if((flag == 1)&&(flag2 == 1)&&(flag3 == 1)){

//             　選択されているアイテム取得
                val categrySpitem = categrySp.selectedItem.toString()
                val syuruiSpitem = syuruiSp.selectedItem.toString()

//           　　 送るようにdata型に変更する
                if (mon.length == 1){ mon = "0"+mon }
                if(day.length == 1){ day = "0"+day }
                val bosyudata = ye+"-"+mon+"-"+day

//                条件選択が指定なし簿場合空白にする
                for (cnt in 1..3) {
                    if(test[cnt] == "指定なし"){
                        test[cnt] =""}
                }

//            入力データ確認
//            var test = arrayListOf("性別","開始年代","終了年代","定員")
                Log.v("alldata","タイトル-"+titleEdit.text.toString()+"種類-"+syuruiSpitem+"カテゴリ-"+categrySpitem+"日時-"+
                        bosyudata+"内容-"+contentEdit.text.toString()+"性別-"+test[0]+"募集年代-"+test[1]+"~"+test[2]+"定員-"+test[3]+b64Encode)



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
            yyyy,
            mm,
            dd)
        datePickerDialog.show()
    }


    //ギャラリーを開くためのメソッド
    private fun openGalleryForImage() {
        //ギャラリーに画面を遷移するためのIntent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        resultData: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == REQUEST_GALLERY_TAKE && resultCode == RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null) {
                uri = resultData.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    photo.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        var baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        jpgarr = baos.toByteArray()
        b64Encode= Base64.getEncoder().encodeToString(jpgarr)

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