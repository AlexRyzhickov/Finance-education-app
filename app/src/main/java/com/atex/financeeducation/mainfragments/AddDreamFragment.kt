package com.atex.financeeducation.mainfragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.atex.financeeducation.R

class AddDreamFragment : Fragment(R.layout.add_dream_fragment) {
    val SELECT_PHOTO: Int = 1
    lateinit var uri: Uri
    lateinit var dreamImg: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dreamImg = view.findViewById(R.id.dream_img)
        view.findViewById<Button>(R.id.image_choose_btn).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.data != null){
            uri = data.data!!
            dreamImg.setImageURI(uri)
        }
    }

}