package com.example.withme

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment


class myDialogFragment (var test:ArrayList<String>,con: Context): DialogFragment() {

    var cont = con
    var values = arrayOf("性別", "年代", "年代", "定員")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // カスタムレイアウトの用意
        val inflater = requireActivity().layoutInflater
        val customAlertView = inflater.inflate(R.layout.dialog, null)
        val spinner = customAlertView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = customAlertView.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = customAlertView.findViewById<Spinner>(R.id.spinner3)
        val men = customAlertView.findViewById<ImageView>(R.id.imageView8)
        val woman = customAlertView.findViewById<ImageView>(R.id.imageView10)

        men.setOnClickListener {
            test[0] = "男"
            woman.setAlpha(100)
            men.setAlpha(255)
        }
        woman.setOnClickListener {
            test[0] = "女"
            woman.setAlpha(255)
            men.setAlpha(100)
        }
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

        val d =  builder.setView(customAlertView).setPositiveButton("決定",null).create()

        d.show()
        d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                test[1] = spinner.selectedItem as String
                test[2] = spinner2.selectedItem as String
                test[3] = spinner3.selectedItem as String

                if(test[1] >= test[2]){
                    Toast.makeText(cont.applicationContext, "期間が間違っています", Toast.LENGTH_SHORT).show()
                }else {
                    val sgender = (cont as postActivity).findViewById(R.id.sgender) as TextView
                    val sage = (cont as postActivity).findViewById(R.id.sage) as TextView
                    val stain = (cont as postActivity).findViewById(R.id.stain) as TextView
                    sgender.setVisibility(View.VISIBLE)
                    sage.setVisibility(View.VISIBLE)
                    stain.setVisibility(View.VISIBLE)
                    sgender.setText("性別：" + test[0])
                    if (test[0].isEmpty()) {
                        sgender.setText("性別：指定なし")
                    }
                    sage.setText("年代：" + test[1] + "～" + test[2] + "")
                    stain.setText("定員：" + test[3])
                    //ダイアログ閉じる
                    d.cancel()
                }
        }
        return d
    }
}