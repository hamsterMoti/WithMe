package com.example.withme

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment

class myDialogFragment (var test:ArrayList<String>,con: Context): DialogFragment() {

    var cont = con
    var values = arrayOf("0", "1", "2", "3")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // カスタムレイアウトの用意
        val inflater = requireActivity().layoutInflater
        val customAlertView = inflater.inflate(R.layout.dialog, null)
        val spinner = customAlertView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = customAlertView.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = customAlertView.findViewById<Spinner>(R.id.spinner3)
        val men = customAlertView.findViewById<Button>(R.id.button4)
        val woman = customAlertView.findViewById<Button>(R.id.button5)
        men.setOnClickListener { values[0] = "男性" }
        woman.setOnClickListener { values[0] = "女性" }
        val age = arrayOf("指定なし", "10", "20", "30", "40", "50", "60", "70")
        val num = arrayOf("指定なし", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

        //希望年齢スピナー
        val adapter = ArrayAdapter(
            cont,
            android.R.layout.simple_spinner_item,
            age
        )

        //募集人数スピナー
        val ageadapter = ArrayAdapter(
            cont,
            android.R.layout.simple_spinner_item,
            num
        )
        spinner.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = ageadapter

        // ダイアログの作成
        val builder = AlertDialog.Builder(activity)
        builder.setView(customAlertView)
            .setPositiveButton("決定") { dialog, id -> // このボタンを押した時の処理を書きます。
                test[1] = spinner.selectedItem as String
                test[2] = spinner2.selectedItem as String
                test[3] = spinner3.selectedItem as String
                Log.v("date", test[1])
            }
        return builder.create()
    }
}