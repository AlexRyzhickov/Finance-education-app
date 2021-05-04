package com.atex.financeeducation.mainfragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.R
import com.atex.financeeducation.viewmodel.CommonViewModel

class AddDreamFragment : Fragment(R.layout.add_dream_fragment) {
    val SELECT_PHOTO: Int = 1
    var uri: Uri? = null
    lateinit var dreamImg: ImageView
    lateinit var dreamName: EditText
    lateinit var dreamCost: EditText
    lateinit var dreamLink: EditText
    private lateinit var viewModel: CommonViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dreamImg = view.findViewById(R.id.dream_img)
        dreamName = view.findViewById(R.id.dream_name)
        dreamCost = view.findViewById(R.id.dream_cost)
        dreamLink = view.findViewById(R.id.dream_link)

        view.findViewById<Button>(R.id.image_choose_btn).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        view.findViewById<Button>(R.id.create_dream_btn).setOnClickListener {
            val costString = dreamCost.text.toString()
            if (!costString.equals("")) {
                val name = dreamName.text.toString()
                val cost = costString.toInt()
                val link = dreamLink.text.toString()
                if (!name.equals("") && cost >= 0 && !link.equals("")) {
                    uri?.let {
                        viewModel.createDream(
                            dreamName.text.toString(),
                            dreamCost.text.toString().toInt(),
                            dreamLink.text.toString(),
                            it
                        )
                    }
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.data != null) {
            uri = data.data
            dreamImg.setImageURI(uri)
        }
    }

}