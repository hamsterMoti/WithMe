package com.example.withme

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import java.util.*

class createaccountActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    private lateinit var birthday: TextView
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

        val emptyError = myApp.emptyError

        birthday = findViewById<TextView>(R.id.birthday)

        birthday.text = "$yyyy/$mm/$dd"

        birthday.setOnClickListener{
            showDatePicker()
        }
        nextButton.setOnClickListener {
            if (nicknameEdit.text.toString().isEmpty()) {
                nicknameEdit.error = emptyError
            } else if (mailaddressEdit.text.toString().isEmpty()) {
                mailaddressEdit.error = emptyError
            } else {
                val nickname = nicknameEdit.text.toString()
                val myId = mailaddressEdit.text.toString()
                val intent = Intent(this, createaccountActivity::class.java)
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
            },
            yyyy,
            mm,
            dd)
        datePickerDialog.show()
    }
}
