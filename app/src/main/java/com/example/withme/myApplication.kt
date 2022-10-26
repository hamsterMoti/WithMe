package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class myApplication : AppCompatActivity() {
    var loginUserId: String = ""
    var apiUrl: String = "http://click.ecc.ac.jp/ecc/whisper_e/"
    val emptyError: String = "入力値がありません"
    val notMatch : String = "ログインIDもしくはパスワードが異なります"
    val connectionError : String = "インターネットに接続できませんでした"
    val unexpectedError: String = "予期せぬエラーが発生しました"

    companion object {
        private var instance : myApplication? = null

        fun  getInstance(): myApplication {
            if (instance == null)
                instance = myApplication()

            return instance!!
        }
    }
}