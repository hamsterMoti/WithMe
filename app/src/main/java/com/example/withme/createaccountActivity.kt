package com.example.withme

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class createaccountActivity : AppCompatActivity() {
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    private lateinit var birthday: TextView
    val cal: Calendar = Calendar.getInstance()
    var oldBirthday = 0
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createaccount)
        supportActionBar //バーを隠す
            ?.hide()

        val nicknameEdit = findViewById<EditText>(R.id.nicknameEdit)
        val mailaddressEdit = findViewById<EditText>(R.id.nicknameEdit)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val genderGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        val menRadio = findViewById<RadioButton>(R.id.menRadioButton)
        var genderText = ""
        var num = 0
        var a = ""
        val emptyError = errormsg.emptyError
//      生年月日
        birthday = findViewById<TextView>(R.id.birthday)

        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH) + 1
        var day = cal.get(Calendar.DATE)
        birthday.text = "0000-00-00"


        birthday.setOnClickListener{
            showDatePicker()
        }

        genderGroup.check(menRadio.id)

        nextButton.setOnClickListener {
            if (nicknameEdit.text.toString().isEmpty()) {
                nicknameEdit.error = emptyError
            } else if (mailaddressEdit.text.toString().isEmpty()) {
                mailaddressEdit.error = emptyError
            }else if(birthday.text.toString().isEmpty()){
                Toast.makeText(applicationContext, emptyError, Toast.LENGTH_SHORT).show()

            } else {
                val nickname = nicknameEdit.text.toString()
                val myId = mailaddressEdit.text.toString()
                val id = genderGroup.checkedRadioButtonId

                when(id) {
                    R.id.menRadioButton -> {//男
                        genderText = "男"
                    }R.id.womanRadioButton -> {//女
                        genderText = "女"

                    }
                }
//                myApp.loginMyId = myId
                var birthGetText = birthday.text
                //生年月日をString型に変換
                birthGetText = birthGetText.toString()
                //生年月日をフォーマットする
                val formatter = DateTimeFormatter.ofPattern("yyyy-[]M-[]d")
                birthGetText = LocalDate.parse(birthGetText, formatter).toString()

                val intent = Intent(this, passwordActivity::class.java)
                intent.putExtra("userId",myId)
                intent.putExtra("userName",nickname)
                intent.putExtra("birthday",birthGetText)
                intent.putExtra("gender",genderText)
                Log.v("genderText",genderText)
                startActivity(intent)
                Log.v("tag", birthGetText)

            }

        }
    }

    private fun showDatePicker() {
        var ye = ""
        var mon = ""
        var day = ""
        val yyyy = cal.get(Calendar.YEAR)
        val mm = cal.get(Calendar.MONTH)
        val dd = cal.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                birthday.text = "${year}-${month + 1}-${dayOfMonth}"
                ye = "${year}"
                mon ="${month+1}"
                day = "${dayOfMonth}"
            },
            yyyy,
            mm,
            dd)
        datePickerDialog.show()
    }
}
