package com.example.withme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class applicantListActivity : AppCompatActivity() {
    val myApp = myApplication.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_list)

        applicantListActivitysub(myApp)

    }

    fun applicantListActivitysub(myapp:myApplication){

        var applicantListRecyeclerView = findViewById<RecyclerView>(R.id.applicantListRecyeclerView)

        //adapterにいれる仮データ（後で変更する）-------------------------------------
        val countList = mutableListOf<applicantDate>()
        for (i in 1..10){
            countList.add(applicantDate(0,"食べ物",0,1))
        }
        applicantListRecyeclerView.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = listapplicantAdapter(countList,this)
        applicantListRecyeclerView.adapter = adapter
        //----------------------------------------------------------------------

    }

}