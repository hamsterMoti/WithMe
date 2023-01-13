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
import com.google.android.material.textfield.TextInputLayout
import okhttp3.OkHttpClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class createaccountActivity : AppCompatActivity() {
//    新規登録の作成の流れ
//    createActivity→passwordActivity→termServiceActivity
    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    val errormsg = errorApplication.getInstance()
    private lateinit var birthyear: Spinner
    private lateinit var birthmonth: Spinner
    private lateinit var birthday: Spinner

//    private lateinit var birthday: TextView
    val cal: Calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
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
        birthyear = findViewById<Spinner>(R.id.birthyear)
        birthmonth = findViewById<Spinner>(R.id.birthmonth)
        birthday = findViewById<Spinner>(R.id.birthday)
        var genderText = ""
        val emptyError = errormsg.emptyError

        val nicknameInputLayout = findViewById<TextInputLayout>(R.id.nickname_layout)
        val mailInputLayout = findViewById<TextInputLayout>(R.id.mailaddress_layout)

        arrayBirth(2030,1990,2030,"年",birthyear)
        arrayBirth(13,1,13,"月",birthmonth)
        arrayBirth(31,1,31,"日",birthday)


        genderGroup.check(menRadio.id)

        nextButton.setOnClickListener {
            if (nicknameEdit.text.toString().isEmpty()) {
                if (mailaddressEdit.text.toString().isEmpty()) {
                    mailInputLayout.error = emptyError
                    nicknameInputLayout.error = emptyError
                }
            } else {
                mailInputLayout.error = null
                nicknameInputLayout.error = null

                Log.v("mail",mailaddressEdit.text.toString())
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

//                // 選択されているbirthyearを取得
                var birthyearItem = birthyear.selectedItem.toString()
//                // 選択されているbirthmonthを取得
                var birthmonthItem = birthmonth.selectedItem.toString()
//                // 選択されているbirthdayを取得
                var birthdayItem = birthday.selectedItem.toString()
                birthyearItem = birthyearItem.replace("年","-")
                birthmonthItem = birthmonthItem.replace("月","-")
                birthdayItem = birthdayItem.removeSuffix("日")


//                replaceBirth(birthyearItem,"年")
//                replaceBirth(birthmonthItem,"月")
//                removeBirth(birthdayItem)
                Log.v("birthdayItem2",birthdayItem)

                var birthGetText = "$birthyearItem$birthmonthItem$birthdayItem"
                Log.v("birthGetText",birthGetText)

//                //生年月日をフォーマットする
                val formatter = DateTimeFormatter.ofPattern("yyyy-[]M-[]d")
                birthGetText = LocalDate.parse(birthGetText, formatter).toString()
                Log.v("birthGetText",birthGetText)


                val intent = Intent(this, passwordActivity::class.java)
                intent.putExtra("userId",myId)
                intent.putExtra("userName",nickname)
                intent.putExtra("birthday",birthGetText)
                intent.putExtra("gender",genderText)
                Log.v("genderText",genderText)
                startActivity(intent)
            }

        }
    }

//    spinnerの値を格納(最大数,いつから,いつまで,年か月か日)
    private fun arrayBirth(maximum:Int,oldNum :Int,newNum:Int,text:String,spinnerName:Spinner)/*: Array<String?> */{
        val arrayBirth = arrayOfNulls<String>(maximum)
        for(i in 0 until newNum){
            arrayBirth[i] = "${i+oldNum}$text"

        }
    val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayBirth)
    spinnerName.adapter = arrayAdapter
    }
//  年と月を"-"に置き換える
    private fun replaceBirth(birthItem:String,birthText:String){
        val birthItem = birthItem.replace(birthText,"-")
    }
//  日を取り除く
    private fun removeBirth(birthItem:String)/*:String*/{
        birthItem.removeSuffix("日")
    }
}
