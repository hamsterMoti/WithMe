package com.example.withme


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context
import android.content.DialogInterface;
import android.content.Intent
import android.os.Bundle;
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment;
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class dialogWithdrawal(con: AppCompatActivity, password:String,flag:String) : DialogFragment() {
    val myApp = myApplication.getInstance()
    var cont = con
    var flag = flag
    var password = password
    val client = OkHttpClient()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("削除")
            .setMessage("本当に削除していいですか？\n※トークも削除されます")
            .setPositiveButton("削除") { dialog, id ->

                if(flag == "withdrawalActivity") {
                    //削除URL
                    var apiUrl =
                        myApp.apiUrl + "userDelete.php?userId=" + myApp.loginMyId + "&password=" + password
                    Log.v("urldata", apiUrl)
                    val request = Request.Builder().url(apiUrl).build()
                    val errorText = "エラー"
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            cont.runOnUiThread {
                                Toast.makeText(
                                    cont.applicationContext,
                                    errorText,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val csvStr = response.body!!.string()
                            val resultError = JSONObject(csvStr)
                            if (resultError.getString("result") == "error") {
                                cont.runOnUiThread {
                                    var errMsg = resultError.getString("errMsg")
                                    Toast.makeText(
                                        cont.applicationContext,
                                        errMsg,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else if (resultError.getString("result") == "success") {
                                cont.runOnUiThread {
                                    Toast.makeText(
                                        cont.applicationContext,
                                        "退会成功しました",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(cont.applicationContext, loginActivity::class.java)
                                    cont.startActivity(intent)
                                }
                            }
                        }
                    })
                }else if(flag == "goodAdapter"){
                    var apiUrl = myApp.apiUrl + "postDelete.php?postNo=" + password
                    val client = OkHttpClient()
                    val request = Request.Builder().url(apiUrl).build()
                    val errorText = "エラー"
                    // Log.v("blockurl",apiUrl)
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            cont.runOnUiThread {
                                Toast.makeText(cont.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            val csvStr = response.body!!.string()
                            val resultError = JSONObject(csvStr)
                            if (resultError.getString("result") == "error") {
                                cont.runOnUiThread {
                                    Toast.makeText(cont.applicationContext, "ブロック済みです", Toast.LENGTH_SHORT).show()
                                }
                            } else if (resultError.getString("result") == "success") {
                                cont.runOnUiThread {
                                    Toast.makeText(cont.applicationContext, "成功", Toast.LENGTH_SHORT).show()
                                        var mypageActivity = cont as mypageActivity
                                        val myApp = myApplication.getInstance()
                                        mypageActivity.mypageActivitysub(myApp)

                                }
                            }
                        }
                })
                }
            }
            .setNegativeButton("キャンセル") { dialog, id ->
                println("dialog:$dialog which:$id")
                Toast.makeText(cont.applicationContext, "キャンセルしました", Toast.LENGTH_SHORT).show()
            }

        return builder.create()
    }

}