package com.example.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class timelineAdapter  (private  val dateSet:MutableList<timelinedata>,private val activity : AppCompatActivity):RecyclerView.Adapter<timelineAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var categoryImage: ImageView
        var statusText: TextView
        var titleText: TextView
        var overviewText: TextView
        var commentNumText: TextView
        var ouboNumText:TextView
        var bosyukigen:TextView
        var zentai: LinearLayout

        init {
            categoryImage = item.findViewById(R.id.imageView6)
            statusText = item.findViewById(R.id.textView7)
            titleText = item.findViewById(R.id.titleText)
            overviewText = item.findViewById(R.id.overviewText)
            commentNumText = item.findViewById(R.id.textView9)
            ouboNumText = item.findViewById(R.id.textView10)
            bosyukigen = item.findViewById(R.id.bosyukigen)
            zentai = item.findViewById(R.id.zentai)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): timelineAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_timeline_adapter, parent, false)

        return ViewHoldew(viwe)
    }

    override fun onBindViewHolder(holder: timelineAdapter.ViewHoldew, position: Int) {

        //データ挿入
        holder.titleText.setText(dateSet[position].titleText)
        holder.overviewText.setText(dateSet[position].overviewText)
        holder.commentNumText.setText(dateSet[position].commentNum)
        holder.ouboNumText.setText(dateSet[position].answerText)
        holder.ouboNumText.setText(dateSet[position].answerText)
        holder.bosyukigen.setText("期限："+dateSet[position].postTime)

        if(dateSet[position].categorynameText == "食べ物"){
            holder.categoryImage.setImageResource(R.drawable.user_6)
        }else if(dateSet[position].categorynameText == "イベント"){
            holder.categoryImage.setImageResource(R.drawable.user_7)
        }else if(dateSet[position].categorynameText == "エンタメ"){
            holder.categoryImage.setImageResource(R.drawable.user_8)
        }else if(dateSet[position].categorynameText == "暮らし"){
            holder.categoryImage.setImageResource(R.drawable.user_9)
        }



        holder.zentai.setOnClickListener{
            var intent = Intent(activity.applicationContext, detailtimelineActivity::class.java)
            intent.putExtra("postNo",dateSet[position].postNo)
            intent.putExtra("recFlag",dateSet[position].recFlag)
            activity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



