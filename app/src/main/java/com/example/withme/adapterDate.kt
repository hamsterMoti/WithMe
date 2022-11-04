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
    var answerText: String

    )

data class talklinedata( //tailadapterのデータ

    var userImage:Int,
    var nameText:String,
    var talkText:String,

    )

data class gooddata( //goodadapterのデータ

    var userImage:Int,
    var contoributorName:String,
    var titleText:String,
    var moreimage:Int,
    var statusImage:Int

    )

data class notificationDate( //notificationのデータ

    var userImage:Int,
    var notificationText:String,

    )

data class blockDate( //blockのデータ

    var userImage:Int,
    var usernameText:String,
    var profileText:String,
    var blockButton:Int,

    )
data class applicantDate(
    var userImage:Int,
    var userText:String,
    var addButton:Int,
    var rejectionButton:Int
)
