package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class goodAdapter  (private  val dateSet:MutableList<gooddata>,private val activity : AppCompatActivity):RecyclerView.Adapter<goodAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var contoributorName:TextView
        var titleText:TextView
        var commentimage:ImageView
        var goodImage:ImageView
        var statusImage:Button


        init {
            userImage = item.findViewById(R.id.userImage)
            contoributorName = item.findViewById(R.id.contoributorName)
            titleText = item.findViewById(R.id.titleText)
            commentimage = item.findViewById(R.id.commentimage)
            goodImage = item.findViewById(R.id.goodImage)
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
        //コメント画像がタップされた時の処理
        holder.commentimage.setOnClickListener{
            //timeline詳細画面へ遷移
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            activity.startActivity(intent)
        }
        //いいね画像がタップされた時の処理
        holder.goodImage.setOnClickListener{
        }
        //ステイタスボタンをタップしたときの処理
        holder.statusImage.setOnClickListener {

        }


    }
    override fun getItemCount(): Int {
        return dateSet.size
    }
}



