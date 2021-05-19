package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.transactions.TransactionFragmentArgs
import com.example.androidkeyboardstatechecker.showToast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GoalsFragment : Fragment(R.layout.goals) {

    private lateinit var changeBotNavViewInterface: ChangeBottomNavView
    private var isClickOpenAddGoal = false
    private val args: GoalsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isClickOpenAddGoal = false

        context?.showToast(isClickOpenAddGoal.toString())

        view.findViewById<FloatingActionButton>(R.id.openAddGoalFragBtn).setOnClickListener {
            val action = GoalsFragmentDirections.actionGoalsFragmentToAddGoalFragment("${args.dreamName}-${args.createDate}-${args.createTime}")
            findNavController().navigate(action)
            isClickOpenAddGoal = true
        }

        activity?.let {
            changeBotNavViewInterface = context as ChangeBottomNavView
            changeBotNavViewInterface.hideBottomNavView()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isClickOpenAddGoal) {
            changeBotNavViewInterface.showBottomNavView()
        }
    }



}