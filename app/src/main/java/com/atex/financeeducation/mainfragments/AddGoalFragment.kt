package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.viewmodel.CommonViewModel

class AddGoalFragment : Fragment(R.layout.add_goal_fragment) {

    private lateinit var editText:EditText
    private val args: AddGoalFragmentArgs by navArgs()
    private lateinit var viewModel: CommonViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.add_goal_text)


        view.findViewById<Button>(R.id.addGoalBtn).setOnClickListener {
            viewModel.createGoal(args.docId,editText.text.toString())
            findNavController().popBackStack()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
    }

}