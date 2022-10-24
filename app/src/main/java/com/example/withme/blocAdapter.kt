package com.example.withme


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class blocAdapter  (private  val dateSet:MutableList<blockDate>):RecyclerView.Adapter<blocAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage: ImageView
        var usernameText: TextView
        var profileText: TextView
        var blockButton: Button

        init {
            userImage = item.findViewById(R.id.userImage)
            usernameText = item.findViewById(R.id.usernameText)
            profileText = item.findViewById(R.id.profileText)
            blockButton = item.findViewById(R.id.blockButton)

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


    }

    override fun getItemCount(): Int {
        return dateSet.size
    }
}



