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

class timelineAdapter  (private  val dateSet:MutableList<timelinedata>):RecyclerView.Adapter<timelineAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var categoryText: TextView
        var categorynameText: TextView
        var statusImage: Button
        var titleText: TextView
        var overviewText: TextView
        var answerText: TextView

        init {
            categoryText = item.findViewById(R.id.categoryText)
            categorynameText = item.findViewById(R.id.categorynameText)
            statusImage = item.findViewById(R.id.statusImage)
            titleText = item.findViewById(R.id.titleText)
            overviewText = item.findViewById(R.id.overviewText)
            answerText = item.findViewById(R.id.answerText)

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

        //仮データ挿入
        holder.categoryText.setText(dateSet[position].categoryText)
        holder.categorynameText.setText(dateSet[position].categorynameText)
        holder.titleText.setText(dateSet[position].titleText)
        holder.overviewText.setText(dateSet[position].overviewText)
        holder.answerText.setText(dateSet[position].answerText)

    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



