package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class errorApplication {
    val emptyError : String = "入力値がありません"
    val notMatch : String = "一致しません"
    val connectionError : String = "通信できませんでした"

    companion object {
        private var instance: errorApplication? = null

        fun getInstance(): errorApplication {
            if (instance == null)
                instance = errorApplication()

            return instance!!
        }
    }
}