package com.example.withme

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context,val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val myApp = myApplication.getInstance()
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            Log.v("viewType", viewType.toString())
//            receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive_msg,parent,false)
            return  ReceiveMegHolder(view)
        }else{
//            sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.send_msg,parent,false)
            return  SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder :: class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else if(holder.javaClass == ReceiveMegHolder :: class.java){
            val viewHolder = holder as ReceiveMegHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int):Int{
        val currentMessage = messageList[position]
        if(myApp.loginMyId == currentMessage.senderId){
            return ITEM_SENT
//        if("aaa@bbb.com" == currentMessage.senderId){
//            return ITEM_SENT
        }else{
            return  ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sendText)
    }
    class ReceiveMegHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.receiveText)

    }

}