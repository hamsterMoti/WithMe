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

class notificationAdapter  (private  val dateSet:MutableList<notificationDate>,private val activity : AppCompatActivity):RecyclerView.Adapter<notificationAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var notificationText:TextView

        init {
            userImage = item.findViewById(R.id.userImage)
            notificationText = item.findViewById(R.id.notificationText)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): notificationAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notification_adapter, parent, false)

        return ViewHoldew(viwe)
    }

    override fun onBindViewHolder(holder: notificationAdapter.ViewHoldew, position: Int) {

        //仮データ挿入
        holder.notificationText.setText(dateSet[position].notificationText)

    }
    override fun getItemCount(): Int {
        return dateSet.size
    }
}



