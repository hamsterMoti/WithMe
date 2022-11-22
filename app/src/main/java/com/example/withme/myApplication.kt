package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class myApplication{
    val ipAddress = "34.229.155.198"
    var apiUrl: String = "http://$ipAddress/with_me/"
//  ID（メールアドレス）
    var loginMyId: String = ""
//  相手のID
    var checkId:String=""



    companion object {
        private var instance : myApplication? = null

        fun  getInstance(): myApplication {
            if (instance == null)
                instance = myApplication()

            return instance!!
        }
    }
}