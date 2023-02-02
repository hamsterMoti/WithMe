package com.example.withme

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context,val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val myApp = myApplication.getInstance()
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2
    private val ITEM_ROOMTEXT = 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.v("viewType", viewType.toString())
        if (viewType == 1) {
//            receive
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.receive_msg, parent, false)
            return ReceiveMegHolder(view)
//        } else if (viewType == 3) {
//            val view: View =
//                LayoutInflater.from(context)
//                    .inflate(R.layout.send_msg, parent, false)
//            return RoomCreateTextHolder(view)
        } else{
//            sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.send_msg, parent, false)
            return SentViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
            holder.sentName.text = currentMessage.name

        } else if (holder.javaClass == ReceiveMegHolder::class.java) {
            val viewHolder = holder as ReceiveMegHolder
            holder.receiveMessage.text = currentMessage.message
            holder.receiveName.text = currentMessage.name

        }
//        } else if (holder.javaClass == RoomCreateTextHolder::class.java) {
//            val viewHolder = holder as RoomCreateTextHolder
//            holder.roomCreateText.text = currentMessage.name + currentMessage.message
//
//        }

    }

    override fun getItemViewType(position: Int) : Int{
        val currentMessage = messageList[position]
        val text = "ルームが作成されました。"
        Log.v("mes[0]",  messageList[0].message.toString())
        if(currentMessage.message.toString()  == text){
            return ITEM_ROOMTEXT
        }else {
            if (myApp.loginMyId == currentMessage.senderId) {
                Log.v("loginId", currentMessage.senderId!!)
                return ITEM_SENT
            } else {
                Log.v("receive", "chat")
                return ITEM_RECEIVE
            }
        }
    }




    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sendText)
        val sentName = itemView.findViewById<TextView>(R.id.sendName)
    }

    class ReceiveMegHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    val receiveMessage = itemView.findViewById<TextView>(R.id.receiveText)
    val receiveName = itemView.findViewById<TextView>(R.id.receiveName)
    }
//    class RoomCreateTextHolder(itemView: View):RecyclerView.ViewHolder(itemView){
//        val roomCreateText = itemView.findViewById<TextView>(R.id.roomCreateText)
//
//    }

}