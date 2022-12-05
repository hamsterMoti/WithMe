package com.example.withme

class Message {
    //    var messageId : String? = null
    var message : String? = null
    var senderId : String? = null
//        var imageUrl : String? = null
//    var timeStramp  : Long = 0
    constructor(){}
    constructor(message : String?, senderId : String?/*, timeStramp  : Long*/){
        this.message = message
        this.senderId = senderId
//        this.timeStramp = timeStramp
    }
}