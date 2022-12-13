package com.example.withme


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class talkAdapter  (private  val dateSet:MutableList<talklinedata>,private val activity : AppCompatActivity):RecyclerView.Adapter<talkAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage: ImageView
        var roomName: TextView
        var talkText: TextView
        var talkzentai : LinearLayout

        init {
            userImage = item.findViewById(R.id.userImage)
            roomName = item.findViewById(R.id.roomName)
            talkText = item.findViewById(R.id.titleText)
            talkzentai = item.findViewById(R.id.talkall)

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

        var sbc = StringBuilder()
        if(dateSet[position].roomName.length >= 15){
            var overview = dateSet[position].roomName.substring(0, 14)
            sbc.append(overview)
            sbc.append("…")
        }else{
            sbc.append(dateSet[position].roomName)
        }
        holder.roomName.setText(sbc)
        sbc = StringBuilder()
        if(dateSet[position].message.length >= 42){
            var overview = dateSet[position].message.substring(0, 41)
            sbc.append(overview)
            sbc.append("…")
        }else{
            sbc.append(dateSet[position].message)
        }
        holder.talkText.setText(sbc)
        if(dateSet[position].categoryText == "食べ物"){
            holder.userImage.setImageResource(R.drawable.user_6)
        }else if(dateSet[position].categoryText == "イベント"){
            holder.userImage.setImageResource(R.drawable.user_7)
        }else if(dateSet[position].categoryText == "エンタメ"){
            holder.userImage.setImageResource(R.drawable.user_8)
        }else if(dateSet[position].categoryText == "暮らし"){
            holder.userImage.setImageResource(R.drawable.user_9)
        }
        holder.talkzentai.setOnClickListener{
            //talk画面へ遷移
            val intent = Intent(activity.applicationContext, chatActivity::class.java)
            intent.putExtra("roomNo",dateSet[position].roomNo)
            activity.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



