package com.example.withme

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

data class timelinedata( //timeLineadapterのデータ情報

    var categoryText: String,
    var categorynameText: String,
    var statusImage: Int,
    var titleText: String,
    var overviewText: String,
    var commentNum:String,
    var answerText: String,
    var postNo: String,
    var recFlag:String,
    var postTime:String

    )

data class talklinedata( //tailadapterのデータ

    var categoryText:String,
    var userId:String,
    var talkText:String,
    var userName:String
    )

data class gooddata( //goodadapterのデータ

    var category:String,
    var contoributorName:String,
    var titleText:String,
    var moreimage:String,
    var statusImage:Int,
    var postNo:String,
    var youid:String,
    var saFlag:Int

    )

data class notificationDate( //notificationのデータ

    var userImage:Int,
    var notificationText:String,

    )

data class blockDate( //blockのデータ

    var userId:String,
    var userName:String,
    var icon:String,
    var profile:String,

    )
data class applicantDate(
    var userImage:Int,
    var userText:String,
    var userId:String,
    var age:String,
    var gender:String,
    var addFlg:Int,
    var postNo:String,
    var roomFlg:Int
)
