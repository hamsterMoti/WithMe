package com.example.withme

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity


class notificationsettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificationsetting)

        var goodSwitch = findViewById<Switch>(R.id.goodSwitch)
        var messageSwitch = findViewById<Switch>(R.id.messageSwitch)

        //ぐっとswitchのイベント処理
        goodSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked) {
                Log.v("goodSw","ON")
            } else {
                Log.v("goodSw","OFF")
            }
        })

        //メッセージSwitchのイベント処理
        messageSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked) {
                Log.v("messageSw","ON")
            } else {
                Log.v("messageSw","OFF")
            }
        })


    }
}