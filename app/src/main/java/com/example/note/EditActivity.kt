package com.example.note

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.note.db.MyDbManager
import com.example.note.db.MyIntentConstants
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {
    val imageRequestCode = 10
    var id = 0
    var isEditState = false
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntents()
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
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            im_MainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    fun onClickSave(view: View) {
        val myTitle = et_EdTitle.text.toString()
        val myDesc = et_edDesk.text.toString()

        if (myTitle != "" && myDesc != "") {
            if (isEditState){
                myDbManager.updateItem(myTitle, myDesc, tempImageUri,id)
            }else {
                myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
            }
            finish()
        }
    }
    fun onEditEnable(view: View){
        et_EdTitle.isEnabled = true
        et_edDesk.isEnabled = true
        fb_Edite.visibility = View.GONE
    }

    fun getMyIntents() {
        fb_Edite.visibility = View.GONE
        val i = intent

        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {
                fb_AddImage.visibility = View.GONE
                et_EdTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                isEditState = true
                et_EdTitle.isEnabled = false
                et_edDesk.isEnabled = false
                fb_Edite.visibility = View.VISIBLE
                et_edDesk.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY,0)
                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty") {

                    mainImageLayout.visibility = View.VISIBLE
                    im_MainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.I_URI_KEY)))
                    im_Delete.visibility = View.GONE
                    im_EditImage.visibility = View.GONE
                }
            }
        }
    }
}