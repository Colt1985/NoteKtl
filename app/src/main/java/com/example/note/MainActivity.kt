package com.example.note

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.note.db.MyDbManager

//Note
class MainActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
    override fun onResume() {
        super.onResume()
        myDbManager.openDb()

    }

    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)

    }


}