package com.example.withme

class Message {
    //    var messageId : String? = null
    var message : String? = null
    var senderId : String? = null
    var name:String?=null
//    var imageUrl : String? = null
//    var timeStramp  : Long = 0
    constructor(){}
    constructor(message : String?, senderId : String?,name:String?/*,imageUrl:String?*/){
        this.message = message
        this.senderId = senderId
        this.name = name
//        this.timeStramp = timeStramp
    }
}