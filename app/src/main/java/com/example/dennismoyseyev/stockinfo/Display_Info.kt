package com.example.dennismoyseyev.stockinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_more_info.*
import okhttp3.*
import java.io.IOException

var myModel = GameDataModel()

class Display_Info : AppCompatActivity() {

    private val moreInfoAdap =information(this, myModel.stocks)
    private val db = DataBaseHandler(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display__info)
        db.readAllData(this, myModel.stocks)
        more_info_list.adapter= moreInfoAdap
        moreInfoAdap.notifyDataSetChanged()
    }


}
