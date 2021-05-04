package com.atex.financeeducation.mainfragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.R
import com.atex.financeeducation.viewmodel.CommonViewModel
import java.util.ArrayList

class AddNoteFragment : Fragment(R.layout.add_note_fragment) {

    lateinit var string1: EditText
    lateinit var string2: EditText
    lateinit var string3: EditText

    lateinit var img1: ImageView
    lateinit var img2: ImageView
    lateinit var img3: ImageView

    lateinit var addBtn: Button

    val list: ArrayList<EditText> = ArrayList()
    private lateinit var viewModel: CommonViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        string1 = view.findViewById(R.id.string_1)
        string2 = view.findViewById(R.id.string_2)
        string3 = view.findViewById(R.id.string_3)

        list.add(string1)
        list.add(string2)
        list.add(string3)

        img1 = view.findViewById(R.id.img_smile_1)
        img2 = view.findViewById(R.id.img_smile_2)
        img3 = view.findViewById(R.id.img_smile_3)

        addBtn = view.findViewById(R.id.addBtn)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var count = 0
                for (editText in list) {
                    if (editText.text.toString().trim().length != 0) count++
                }

                when (count) {
                    0 -> {
                        animateImgAlpha(img1, false)
                    }
                    1 -> {
                        animateImgAlpha(img1, true)
                        animateImgAlpha(img2, false)
                    }
                    2 -> {
                        animateImgAlpha(img2, true)
                        animateImgAlpha(img3, false)
                        addBtn.isEnabled = false
                    }
                    3 -> {
                        addBtn.isEnabled = true
                        animateImgAlpha(img3, true)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        string1.addTextChangedListener(textWatcher)
        string2.addTextChangedListener(textWatcher)
        string3.addTextChangedListener(textWatcher)

        addBtn.setOnClickListener {
            viewModel.addNotes(
                string1.text.toString(),
                string2.text.toString(),
                string3.text.toString(),
                context
            )
            findNavController().popBackStack()
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
    }


    fun animateImgAlpha(view: View, isShow: Boolean) {
        val value = if (isShow) 1f else 0.3f
        val anim = ObjectAnimator.ofFloat(view, "alpha", value)
        anim.duration = 200 // duration 3 seconds
        anim.start()
    }

}