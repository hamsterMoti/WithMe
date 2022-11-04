package com.example.withme

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class goodAdapter  (private  val dateSet:MutableList<gooddata>,private val activity : AppCompatActivity):RecyclerView.Adapter<goodAdapter.ViewHoldew>() {

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
            //マイページへ遷移
            var intent = Intent(activity.applicationContext, mypageActivity::class.java)
            activity.startActivity(intent)
        }
        //ユーザネームがタップされた時の処理
        holder.contoributorName.setOnClickListener{
            //マイページへ遷移
            var intent = Intent(activity.applicationContext, mypageActivity::class.java)
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

            Log.v("nameaa",dateSet[position].moreimage)

            if (dateSet[position].moreimage.equals("投稿一覧")){ //投稿一覧リストの場合
                val myBottomSheet = ntmsheet()
                myBottomSheet.show(activity.supportFragmentManager,"navigation_bottom_sheet")
            }else if (dateSet[position].moreimage.equals("参加一覧")){ //参加一覧リストの場合
                val myBottomSheet = btmsheet_Participation()
                myBottomSheet.show(activity.supportFragmentManager,"navigation_bottom_sheet")
            }

        }
        //ステイタスボタンをタップしたときの処理
        holder.statusImage.setOnClickListener {
        }

    }
    override fun getItemCount(): Int {
        return dateSet.size
    }
}



