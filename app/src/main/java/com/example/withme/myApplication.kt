package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class myApplication{
    val ipAddress = "34.229.155.198"
    var apiUrl: String = "http://$ipAddress/with_me/"
//  ID（メールアドレス）
    var loginMyId: String = ""
//  ニックネーム
    var nickname : String = ""
//  性別
    var gender:Int = 0
//  年齢
    var age : Int = 0
//  年(キャスト前）
    var oldyear : String = ""
//  月(キャスト前）
    var oldmonth : String = ""
//  日(キャスト前）
    var oldday : String = ""
//  年(キャスト後）
    var year : String = ""
//  月(キャスト後）
    var month : String = ""
//  日(キャスト後）
    var day : String = ""



    companion object {
        private var instance : myApplication? = null

        fun  getInstance(): myApplication {
            if (instance == null)
                instance = myApplication()

            return instance!!
        }
    }
}