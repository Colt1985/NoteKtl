package com.example.note

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.note.db.MyDbManager
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {
    val imageRequestCode = 10
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
    override fun onResume() {
        super.onResume()
        myDbManager.openDb()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            im_MainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }
    }

    fun onClickAddImage(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        fb_AddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        mainImageLayout.visibility = View.GONE
        fb_AddImage.visibility = View.VISIBLE
    }

    fun onClickChoseImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    fun onClickSave(view: View) {
        val myTitle = et_EdTitle.text.toString()
        val myDesc = et_edDesk.text.toString()

        if(myTitle!="" && myDesc !=""){
myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
        }
            }
}