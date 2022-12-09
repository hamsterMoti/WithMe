package com.example.withme

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class listapplicantAdapter  (private  val dateSet:MutableList<applicantDate>,private val activity : AppCompatActivity): RecyclerView.Adapter<listapplicantAdapter.ViewHoldew>() {

    val myApp = myApplication.getInstance()
    val client = OkHttpClient()

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var userText:TextView
        var agegender:TextView
        var addButton:Button
        var rejectionButton	:Button

        init {
            userImage = item.findViewById(R.id.userImage)
            userText = item.findViewById(R.id.userText)
            agegender = item.findViewById(R.id.agegender)
            addButton = item.findViewById(R.id.addButton)
            rejectionButton = item.findViewById(R.id.rejectionButton)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listapplicantAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listapplicant_adapter, parent, false)
        return listapplicantAdapter.ViewHoldew(viwe)
    }
    override fun onBindViewHolder(holder: listapplicantAdapter.ViewHoldew, position: Int) {

        //データ挿入
        holder.userText.setText(dateSet[position].userText)
        holder.agegender.setText(dateSet[position].age+"歳　"+dateSet[position].gender)
        Log.v("kakunin","adapterstart"+dateSet[position].userText)

        if(dateSet[position].addFlg == 2){//既にグループに追加済みの場合
            holder.rejectionButton.setVisibility(View.INVISIBLE)
            holder.addButton.setText("追加済")
        }

            //追加ボタンタップ処理
            holder.addButton.setOnClickListener {
                Log.v("kakunin","ボタン押された")
                if(dateSet[position].roomFlg==1){
//                    Toast.makeText(activity.applicationContext, "部屋がありません", Toast.LENGTH_SHORT).show()
                    var apiUrl = myApp.apiUrl+"roomCreate.php?postNo="+dateSet[position].postNo+"&userId="+myApp.loginMyId
                    val request = Request.Builder().url(apiUrl).build()
                    val errorText = "エラー"
                    Log.v("blockurl", apiUrl.toString())
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            activity.runOnUiThread {
                                Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            val csvStr = response.body!!.string()
                            val resultError = JSONObject(csvStr)
                            if(resultError.getString("result") == "error") {
                                activity.runOnUiThread {
                                    Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }else if(resultError.getString("result") == "success"){
                                activity.runOnUiThread {
//                                    applicantListActivitysub(myApp,postNo)
                                    Toast.makeText(activity.applicationContext, "成功", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    })

                }
                if(dateSet[position].addFlg == 2) {
                    Toast.makeText(activity.applicationContext, "追加済みです", Toast.LENGTH_SHORT).show()
                }else{
                    //あとで可能なら変更する
//                    holder.rejectionButton.setVisibility(View.INVISIBLE)
//                    holder.addButton.setText("追加済み")
                    //ユーザ追加処理
                    var apiUrl = myApp.apiUrl+"memberAdd.php?userId="+dateSet[position].userId+"&roomNo="+dateSet[position].postNo
                    val request = Request.Builder().url(apiUrl).build()
                    val errorText = "エラー"
                    Log.v("blockurl", apiUrl.toString())
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            activity.runOnUiThread {
                                Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            val csvStr = response.body!!.string()
                            val resultError = JSONObject(csvStr)
                            if(resultError.getString("result") == "error") {
                                activity.runOnUiThread {
                                    Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                                }
                            }else if(resultError.getString("result") == "success"){
                                activity.runOnUiThread {
                                    var applicantListActivity = activity as applicantListActivity
                                    val myApp = myApplication.getInstance()
                                    applicantListActivity.applicantListActivitysub(myApp,dateSet[position].postNo)
                                }
                            }
                        }
                    })

            }

        }

        //拒否ボタンタップ処理
        holder.rejectionButton.setOnClickListener {

            var apiUrl = myApp.apiUrl+"applyDeny.php?userId="+dateSet[position].userId+"&postNo="+dateSet[position].postNo
            val request = Request.Builder().url(apiUrl).build()
            val errorText = "エラー"
            Log.v("blockurl", apiUrl.toString())
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity.runOnUiThread {
                        Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val csvStr = response.body!!.string()
                    val resultError = JSONObject(csvStr)
                    if(resultError.getString("result") == "error") {
                        activity.runOnUiThread {
                            Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                        }
                    }else if(resultError.getString("result") == "success"){
                        activity.runOnUiThread {
                            var applicantListActivity = activity as applicantListActivity
                            val myApp = myApplication.getInstance()
                            applicantListActivity.applicantListActivitysub(myApp,dateSet[position].postNo)
                        }
                    }
                }
            })


        }

    }

    override fun getItemCount(): Int {
        return dateSet.size
    }

}

