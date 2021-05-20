package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.GoalsAdapter
import com.atex.financeeducation.adapters.SliderAdapter
import com.atex.financeeducation.data.DreamItem
import com.atex.financeeducation.data.GoalItem
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.example.androidkeyboardstatechecker.showToast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GoalsFragment : Fragment(R.layout.goals_fragment), SliderAdapter.OnItemClickListener {

    private lateinit var changeBotNavViewInterface: ChangeBottomNavView
    private var isClickOpenAddGoal = false
    private val args: GoalsFragmentArgs by navArgs()
    private lateinit var recycler: RecyclerView
    private lateinit var goalAdapter: GoalsAdapter
    private val db = Firebase.firestore
    private lateinit var viewModel: CommonViewModel

    private lateinit var sliderAdapter: SliderAdapter

    private val users = db.collection("Users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isClickOpenAddGoal = false

        view.findViewById<FloatingActionButton>(R.id.openAddGoalFragBtn).setOnClickListener {
            val action = GoalsFragmentDirections.actionGoalsFragmentToAddGoalFragment("${args.dreamName}-${args.createDate}-${args.createTime}")
            findNavController().navigate(action)
            isClickOpenAddGoal = true
        }

        activity?.let {
            changeBotNavViewInterface = context as ChangeBottomNavView
            changeBotNavViewInterface.hideBottomNavView()
        }

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        val query: Query = users.document("makseljoinb@gmail.com").collection("dreams")
            .document("Airpods-04.05.2021-15:10:15").collection("goals")
        val options = FirestoreRecyclerOptions.Builder<GoalItem>()
            .setQuery(query, GoalItem::class.java)
            .build()

        recycler = view.findViewById(R.id.recyclerGoals2)
        goalAdapter = GoalsAdapter(options)
        recycler.adapter = goalAdapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isClickOpenAddGoal) {
            changeBotNavViewInterface.showBottomNavView()
        }
    }

    override fun onStart() {
        super.onStart()
        goalAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        goalAdapter.stopListening()
    }

    override fun onItemClick(createDate: String, createTime: String, dreamName: String) {

    }


}