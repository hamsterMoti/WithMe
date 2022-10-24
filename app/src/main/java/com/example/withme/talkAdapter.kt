package com.example.withme


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class talkAdapter  (private  val dateSet:MutableList<talklinedata>):RecyclerView.Adapter<talkAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage: ImageView
        var nameText: TextView
        var talkText: TextView

        init {
            userImage = item.findViewById(R.id.userImage)
            nameText = item.findViewById(R.id.contoributorName)
            talkText = item.findViewById(R.id.titleText)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): talkAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_talk_adapter, parent, false)

        return talkAdapter.ViewHoldew(viwe)
    }
    override fun onBindViewHolder(holder: talkAdapter.ViewHoldew, position: Int) {

        //仮データ挿入
        holder.nameText.setText(dateSet[position].nameText)
        holder.talkText.setText(dateSet[position].talkText)


    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



