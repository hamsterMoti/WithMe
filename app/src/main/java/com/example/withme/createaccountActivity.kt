package com.example.withme

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import java.util.*

class createaccountActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()

    private lateinit var birthday: TextView
    var yearLog = ""
    var monthLog = ""
    var dayLog = ""

    var cal: Calendar = Calendar.getInstance()
    // 年を取得
    val yyyy = cal.get(Calendar.YEAR)
    // 月を取得(ただし0〜11のため、1を加算する)
    val mm = cal.get(Calendar.MONTH)
    // 日を取得
    val dd = cal.get(Calendar.DAY_OF_MONTH)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createaccount)
        supportActionBar //バーを隠す
            ?.hide()

        val nicknameEdit = findViewById<EditText>(R.id.nicknameEdit)
        val mailaddressEdit = findViewById<EditText>(R.id.mailaddressEdit)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val genderGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        val menRadio = findViewById<RadioButton>(R.id.menRadioButton)
        val id = genderGroup.checkedRadioButtonId
        var number = 0
        val emptyError = errormsg.emptyError
//      生年月日
        birthday = findViewById<TextView>(R.id.birthday)

        birthday.text = "$yyyy/$mm/$dd"

        birthday.setOnClickListener{
            showDatePicker()

        }

        genderGroup.check(menRadio.id)

        nextButton.setOnClickListener {
            if (nicknameEdit.text.toString().isEmpty()) {
                nicknameEdit.error = emptyError
            } else if (mailaddressEdit.text.toString().isEmpty()) {
                mailaddressEdit.error = emptyError
            }else if(yearLog.isEmpty()){
                birthday.error = emptyError

            } else {
                val nickname = nicknameEdit.text.toString()
                val myId = mailaddressEdit.text.toString()
                when(id) {
                    R.id.menRadioButton -> {//男
                        number = 1
                    }
                    R.id.womanRadioButton -> {//女
                        number = 2
                    }
                }
                Log.v("num",number.toString())
                myApp.loginMyId = myId
//                myApp.nickname = nickname
//                myApp.year = yearLog.toInt().toString()
                val num1 = 0
                Log.v("j", yearLog.toInt().toString())
//                myApp.month = monthLog.toInt().toString()
//                myApp.day = dayLog.toInt().toString()
                val intent = Intent(this, passwordActivity::class.java)
                startActivity(intent)
            }

        }
    }
    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                birthday.text = "${year}/${month + 1}/${dayOfMonth}"
                yearLog = "$year"
                monthLog = "$month"
                dayLog = "$dayOfMonth"
            },
            yyyy,
            mm,
            dd)
        datePickerDialog.show(

        )
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
