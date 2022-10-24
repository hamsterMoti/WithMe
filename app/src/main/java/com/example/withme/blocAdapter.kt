package com.example.withme


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class blocAdapter  (private  val dateSet:MutableList<blockDate>,private val activity : AppCompatActivity):RecyclerView.Adapter<blocAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage: ImageView
        var usernameText: TextView
        var profileText: TextView
        var blockButton: Button
        var sonota: LinearLayout

        init {
            userImage = item.findViewById(R.id.userImage)
            usernameText = item.findViewById(R.id.usernameText)
            profileText = item.findViewById(R.id.profileText)
            blockButton = item.findViewById(R.id.blockButton)
            sonota = item.findViewById(R.id.sonota)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): blocAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_bloc_adapter, parent, false)

        return blocAdapter.ViewHoldew(viwe)
    }
    override fun onBindViewHolder(holder: blocAdapter.ViewHoldew, position: Int) {


        //ブロック解除ボタンタップ
        holder.blockButton.setOnClickListener {
            var intent = Intent(activity.applicationContext, MainActivity::class.java)
            activity.startActivity(intent)
        }
        //ボタン以外タップの場合
        holder.sonota.setOnClickListener {
            var intent = Intent(activity.applicationContext, mypageActivity::class.java)
            activity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



