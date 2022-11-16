package com.example.withme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class timelineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        val timelineRecycl = findViewById<RecyclerView>(R.id.profileRecyclerView)
        val fab: View = findViewById(R.id.fab)
//        絞り込み
        val adjustImage = findViewById<ImageView>(R.id.adjustImage)
//        並び替え
        val tuneImage = findViewById<ImageView>(R.id.tuneImage)

        //FABボタンタップ処理
        fab.setOnClickListener { view ->
            var intent = Intent(applicationContext, postActivity::class.java)
            startActivity(intent)
        }


        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<timelinedata>()
        for (i in 1..10) {
            countList.add(timelinedata("カテゴリー：", "食べ物", 0, "食べ放題行く人", "大阪で募集", "解答件数"))
        }
        timelineRecycl.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = timelineAdapter(countList, this)
        timelineRecycl.adapter = adapter
        //----------------------------------------------------------------------

        adjustImage.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@timelineActivity, R.style.BottomSheetDialogTheme

            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.adjustbottomseet,
                findViewById(R.id.bottomSheet) as LinearLayout?
            )
            bottomSheetView.findViewById<View>(R.id.newPost).setOnClickListener {
//          新着順に並び替える
                Log.v("newpost","新着順")
//                Toast.makeText(this@timelineActivity, "cancel", Toast.LENGTH_SHORT).show()
            }
            bottomSheetView.findViewById<View>(R.id.oldPost).setOnClickListener {
//          投稿順に並び替える
                Log.v("oldpost","投稿順")
            }
            bottomSheetView.findViewById<View>(R.id.oldPost).setOnClickListener {
//          締め切り近いに並び替える
                Log.v("deadlinePost","締め切り近い")
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        tuneImage.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@timelineActivity, R.style.BottomSheetDialogTheme
            )
            val tunebottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.tune_bottom_sheet,
                findViewById(R.id.tunebottomSheet) as LinearLayout?
            )

            bottomSheetDialog.setContentView(tunebottomSheetView)
            bottomSheetDialog.show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val timeline = Intent(this, timelineActivity::class.java)
        val chat = Intent(this, chatlistActivity::class.java)
        val notification = Intent(this, notificationActivity::class.java)
        val mypage = Intent(this, mypageActivity::class.java)
        val settingsettinglist = Intent(this, settinglistActivity::class.java)
        val login = Intent(this, loginActivity::class.java)
        //連携処理を実施

        when (item.itemId) {
            R.id.timeline -> startActivity(timeline)
            R.id.chat -> startActivity(chat)
            R.id.notification -> startActivity(notification)
            R.id.mypage -> startActivity(mypage)
            R.id.setting -> startActivity(settingsettinglist)

            else -> startActivity(login)//ログアウト処理を書く
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }
}