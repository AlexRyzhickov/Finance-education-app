package com.atex.financeeducation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.atex.financeeducation.R
import com.google.android.material.textfield.TextInputLayout

class ExpensesDialog: DialogFragment() {

    lateinit var textField: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.expenses_dialog_fragment,container,false)


        textField = rootView.findViewById(R.id.textField)
        textField.setStartIconTintList(null)

        return rootView
    }

}