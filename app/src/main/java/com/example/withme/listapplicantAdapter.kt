package com.example.withme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class listapplicantAdapter  (private  val dateSet:MutableList<applicantDate>,private val activity : AppCompatActivity): RecyclerView.Adapter<listapplicantAdapter.ViewHoldew>() {

    class ViewHoldew(item: View) : RecyclerView.ViewHolder(item) {
        var userImage:ImageView
        var userText:TextView
        var addButton:Button
        var rejectionButton	:Button

        init {
            userImage = item.findViewById(R.id.userImage)
            userText = item.findViewById(R.id.userText)
            addButton = item.findViewById(R.id.addButton)
            rejectionButton = item.findViewById(R.id.rejectionButton)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listapplicantAdapter.ViewHoldew {
        //一行分のレイアウト読みこみ
        val viwe = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listapplicant_adapter, parent, false)
        return listapplicantAdapter.ViewHoldew(viwe)
    }
    override fun onBindViewHolder(holder: listapplicantAdapter.ViewHoldew, position: Int) {

        //データ挿入
        holder.userText.setText(dateSet[position].userText)

        //追加ボタンタップ処理
        holder.addButton.setOnClickListener {
            //applicantListActivityのapplicantListActivitysub実行
            var applicantListActivity = activity as applicantListActivity
            val myApp = myApplication.getInstance()
            applicantListActivity.applicantListActivitysub(myApp)
        }
        //拒否ボタンタップ処理
        holder.rejectionButton.setOnClickListener {
            //applicantListActivityのapplicantListActivitysub実行
            var applicantListActivity = activity as applicantListActivity
            val myApp = myApplication.getInstance()
            applicantListActivity.applicantListActivitysub(myApp)
        }

    }

    override fun getItemCount(): Int {
        return dateSet.size
    }

}

