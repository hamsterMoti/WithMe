//package com.example.withme
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationCompat.getContentText
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.core.app.NotificationCompat.Builder as Builder1
//
//class CompatNotification(con: Context) {
//
//    var cont = con
//    val CHANNEL_ID = "sample"
//
//    // 通知チャンネルの作成
//     fun createNotificationChannel() {
//        val CHANNEL_ID = "sample"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "name"
//            val descriptionText = "description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//              //  getSystemService(cont.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//
//    fun aaa() {
//
//        createNotificationChannel()
//        tuuti()
//
//    }
//
//    fun tuuti(){
//
//        //val CHANNEL_ID = "sample"
//        val intent = Intent(cont, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(cont, 0, intent, 0)
//
//        //通知オブジェクトの作成
//        var builder = Builder1(cont, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Title")
//            .setContentText("Text")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        //通知の実施
//        val notificationManager: NotificationManager =
//            getSystemService(cont.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, builder.build())
//    }
//}