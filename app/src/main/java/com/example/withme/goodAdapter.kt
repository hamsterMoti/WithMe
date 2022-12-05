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
        if(dateSet[position].statusImage==1){
            holder.statusImage.setText("募集中")
        }else{
            holder.statusImage.setText("締切")
        }
        if(dateSet[position].category == "食べ物"){
            holder.userImage.setImageResource(R.drawable.user_6)
        }else if(dateSet[position].category == "イベント"){
            holder.userImage.setImageResource(R.drawable.user_7)
        }else if(dateSet[position].category == "エンタメ"){
            holder.userImage.setImageResource(R.drawable.user_8)
        }else if(dateSet[position].category == "暮らし"){
            holder.userImage.setImageResource(R.drawable.user_9)
        }



        //アイコン画像がタップされた時の処理
        holder.userImage.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            intent.putExtra("postNo",dateSet[position].postNo)
            Log.v("postNo",dateSet[position].postNo)
            activity.startActivity(intent)
        }
        //ユーザネームがタップされた時の処理
        holder.contoributorName.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            intent.putExtra("postNo",dateSet[position].postNo)
            Log.v("postNo",dateSet[position].postNo)
            activity.startActivity(intent)
        }
        //投稿内容がタップされた時の処理
        holder.titleText.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            intent.putExtra("postNo",dateSet[position].postNo)
            Log.v("postNo",dateSet[position].postNo)
            activity.startActivity(intent)
        }
        //more画像がタップされた時ボトムシートを表示
        holder.moreimage.setOnClickListener{
            if (dateSet[position].moreimage.equals("投稿一覧")) { //投稿一覧リストの場合
                if(dateSet[position].saFlag == 1) {
                    val bottomSheetDialog = BottomSheetDialog(
                        activity, R.style.BottomSheetDialogTheme
                    )
                    val bottomSheetView = LayoutInflater.from(activity).inflate(
                        R.layout.activity_ntmsheet,
                        activity.findViewById(R.id.tune) as LinearLayout?
                    )
                    bottomSheetView.findViewById<View>(R.id.textView5).setOnClickListener {
                        Log.v("newpost", "投稿削除")
                        var apiUrl =
                            myApp.apiUrl + "postDelete.php?postNo=" + dateSet[position].postNo
                        okituusinn(apiUrl, activity)
                        //bottomシートclause
                        bottomSheetDialog.dismiss()
                    }
                    bottomSheetDialog.setContentView(bottomSheetView)
                    bottomSheetDialog.show()
                }
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
                    //bottomシートclause
                    bottomSheetDialog.dismiss()
                }
                bottomSheetView.findViewById<View>(R.id.textView3).setOnClickListener {
                    Log.v("newpost","通報")
                    //bottomシートclause
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }

        }
        //ステイタスボタンをタップしたときの処理
        holder.statusImage.setOnClickListener {

          if((myApp.loginMyId==myApp.checkId)&&(dateSet[position].moreimage=="投稿一覧")){
              var apiUrl = myApp.apiUrl+"statusCtl.php?userId="+myApp.loginMyId+"&postNo="+dateSet[position].postNo+"&statusFlg="+dateSet[position].statusImage
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
                              holder.statusImage.setText("")
                              var mypageActivity = activity as mypageActivity
                              val myApp = myApplication.getInstance()
                              mypageActivity.mypageActivitysub(myApp)
                          }
                      }
                  }
              })
          }

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
                    var mypageActivity = activity as mypageActivity
                    val myApp = myApplication.getInstance()
                    mypageActivity.mypageActivitysub(myApp)
                }
            }
        }
    })
}




