package com.example.withme

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class goodAdapter  (private  val dateSet:MutableList<gooddata>,private val activity : AppCompatActivity):RecyclerView.Adapter<goodAdapter.ViewHoldew>() {

    val client = OkHttpClient()
    val myApp = myApplication.getInstance()
    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var contoributorName:TextView
        var titleText:TextView
        var moreimage:ImageView
        var statusImage:Button


        init {
            userImage = item.findViewById(R.id.userImage)
            contoributorName = item.findViewById(R.id.contoributorName)
            titleText = item.findViewById(R.id.titleText)
            moreimage = item.findViewById(R.id.moreImage)
            statusImage = item.findViewById(R.id.statusImage)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): goodAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_good_adapter, parent, false)

        return ViewHoldew(viwe)
    }

    override fun onBindViewHolder(holder: goodAdapter.ViewHoldew, position: Int) {
        //データ挿入
        holder.contoributorName.setText(dateSet[position].contoributorName)
        holder.titleText.setText(dateSet[position].titleText)

        //アイコン画像がタップされた時の処理
        holder.userImage.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            activity.startActivity(intent)
        }
        //ユーザネームがタップされた時の処理
        holder.contoributorName.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            activity.startActivity(intent)
        }
        //投稿内容がタップされた時の処理
        holder.titleText.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            activity.startActivity(intent)
        }
        //more画像がタップされた時ボトムシートを表示
        holder.moreimage.setOnClickListener{
            if (dateSet[position].moreimage.equals("投稿一覧")){ //投稿一覧リストの場合
                val bottomSheetDialog = BottomSheetDialog(
                    activity, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_ntmsheet,
                    activity.findViewById(R.id.tune) as LinearLayout?
                )
                bottomSheetView.findViewById<View>(R.id.textView5).setOnClickListener {
                    Log.v("newpost","投稿削除")
                    var apiUrl = myApp.apiUrl + "deletePost.php?postNo="+dateSet[position].postNo
                    okituusinn(apiUrl,activity)
                }

                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }else if (dateSet[position].moreimage.equals("参加一覧")){ //参加一覧リストの場合
                val bottomSheetDialog = BottomSheetDialog(
                    activity, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_btmsheet_participation,
                    activity.findViewById(R.id.tune) as LinearLayout?
                )
                bottomSheetView.findViewById<View>(R.id.textView5).setOnClickListener {
                    Log.v("newpost","ブロック")
                    var apiUrl = myApp.apiUrl + "blockCtl.php?userId=" + myApp.loginMyId + "&blockUserId=" + dateSet[position].youid + "&blockFlg=1"
                    okituusinn(apiUrl,activity)
                }
                bottomSheetView.findViewById<View>(R.id.textView3).setOnClickListener {
                    Log.v("newpost","通報")
                }
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }

        }
        //ステイタスボタンをタップしたときの処理
        holder.statusImage.setOnClickListener {
            var flag = 1
            if(dateSet[position].statusImage==1){
                flag = 2
            }else{
                flag = 1
            }
            var apiUrl = myApp.apiUrl+"statusCtl.php?userId="+myApp.loginMyId+"&postNo="+dateSet[position].postNo+"&statusFlg="+flag
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
                            var mypageActivity = activity as mypageActivity
                            val myApp = myApplication.getInstance()
                            mypageActivity.mypageActivitysub(myApp)
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

fun okituusinn(url:String,activity:AppCompatActivity){
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val errorText = "エラー"
    // Log.v("blockurl",apiUrl)
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            activity.runOnUiThread {
                Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
            }
        }
        override fun onResponse(call: Call, response: Response) {
            val csvStr = response.body!!.string()
            val resultError = JSONObject(csvStr)
            if (resultError.getString("result") == "error") {
                activity.runOnUiThread {
                    Toast.makeText(activity.applicationContext, errorText, Toast.LENGTH_SHORT).show()
                }
            } else if (resultError.getString("result") == "success") {
                activity.runOnUiThread {
                }
            }
        }
    })
}




