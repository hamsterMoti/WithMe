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

class goodAdapter  (private  val dateSet:MutableList<gooddata>):RecyclerView.Adapter<goodAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var contoributorName:TextView
        var titleText:TextView
        var commentimage:ImageView
        var goodImage:ImageView


        init {
            userImage = item.findViewById(R.id.userImage)
            contoributorName = item.findViewById(R.id.contoributorName)
            titleText = item.findViewById(R.id.titleText)
            commentimage = item.findViewById(R.id.commentimage)
            goodImage = item.findViewById(R.id.goodImage)

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
        //仮データ挿入
        holder.contoributorName.setText(dateSet[position].contoributorName)
        holder.titleText.setText(dateSet[position].titleText)

    }
    override fun getItemCount(): Int {
        return dateSet.size
    }
}



