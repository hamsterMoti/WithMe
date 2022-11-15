package com.example.withme


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class blocAdapter  (private  val dateSet:MutableList<blockDate>,private val activity : AppCompatActivity):RecyclerView.Adapter<blocAdapter.ViewHoldew>() {
    val client = OkHttpClient()
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


        holder.usernameText.setText(dateSet[position].userName)
        holder.profileText.setText(dateSet[position].profile)

        //ブロック解除ボタンタップ
        holder.blockButton.setOnClickListener {

            val URL = "http://34.229.9.247/with_me/blockCtl.php?userId=2200166@ecc.ac.jp&blockUserId="+dateSet[position].userId+"&blockFlg=2"


            val request = Request.Builder().url(URL).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity.runOnUiThread {
                        Toast.makeText(activity, "erorr", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val csvStr = response.body!!.string()
                    val resultError = JSONObject(csvStr)
                    if (resultError.getString("result") == "error") {
                        activity.runOnUiThread {
                            Toast.makeText(activity, "erorr", Toast.LENGTH_SHORT).show()
                        }
                    } else if (resultError.getString("result") == "success") {
                        activity.runOnUiThread {
                            var blockActivity = activity as blockActivity
                            val myApp = myApplication.getInstance()
                            blockActivity.blockActivitysub(myApp)
                        }
                    }
                }
            })



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



