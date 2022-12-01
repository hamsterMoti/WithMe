package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class myApplication{
    val ipAddress = "184.73.14.60"
    var apiUrl: String = "http://$ipAddress/with_me/"
//  ID（メールアドレス）
    var loginMyId: String = "aaa@bbb.com"
//  相手のID
    var checkId:String="aaa@bbb.com"



    companion object {
        private var instance : myApplication? = null

        fun  getInstance(): myApplication {
            if (instance == null)
                instance = myApplication()

            return instance!!
        }
    }
}