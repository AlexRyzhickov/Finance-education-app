package com.atex.financeeducation.mainfragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.GoalsAdapter
import com.atex.financeeducation.data.GoalItem
import com.atex.financeeducation.enums.Articles
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.example.androidkeyboardstatechecker.showToast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GoalsFragment : Fragment(R.layout.goals_fragment), GoalsAdapter.OnGoalClickListener {

    private lateinit var changeBotNavViewInterface: ChangeBottomNavView
    private var isClickOpenAddGoal = false
    private val args: GoalsFragmentArgs by navArgs()
    private lateinit var recycler: RecyclerView
    private lateinit var goalAdapter: GoalsAdapter
    private val db = Firebase.firestore
    private lateinit var viewModel: CommonViewModel
    private val users = db.collection("Users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isClickOpenAddGoal = false

        view.findViewById<FloatingActionButton>(R.id.openAddGoalFragBtn).setOnClickListener {
            val action =
                GoalsFragmentDirections.actionGoalsFragmentToAddGoalFragment(getDreamDocId())
            findNavController().navigate(action)
            isClickOpenAddGoal = true
        }

        view.findViewById<TextView>(R.id.dreamName).text = "Мечта: ${args.dreamName}"
        view.findViewById<TextView>(R.id.dreamDate).text = "Дата создания: ${args.createDate}"

        view.findViewById<Button>(R.id.deleteDream).setOnClickListener {
            getDream(args.dreamCost)
        }

        view.findViewById<ImageView>(R.id.article_goals_btn).setOnClickListener {
            val action = GoalsFragmentDirections.actionGoalsFragmentToArticleFragment(Articles.GOALS)
            findNavController().navigate(action)
        }

        activity?.let {
            changeBotNavViewInterface = context as ChangeBottomNavView
            changeBotNavViewInterface.hideBottomNavView()
        }

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        val query: Query = users.document(viewModel.email).collection("dreams")
            .document(getDreamDocId()).collection("goals")
        val options = FirestoreRecyclerOptions.Builder<GoalItem>()
            .setQuery(query, GoalItem::class.java)
            .build()

        recycler = view.findViewById(R.id.recyclerGoals2)
        goalAdapter = GoalsAdapter(this, options)
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

    fun onAlertDialog(done: Boolean, position: Int) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Изменить статус карточки ?")

        if (done)
            builder.setMessage("Пометить шаг как невыполненный")
        else
            builder.setMessage("Пометить шаг как выполненный")

        builder.setPositiveButton(
            "Изменить статус"
        ) { dialog, id ->
            Toast.makeText(context, "Статус цели изменён", Toast.LENGTH_SHORT).show()
            viewModel.updateGoal(goalAdapter.getItemRef(position))
        }

        builder.setNegativeButton(
            "Отмена"
        ) { dialog, id ->

        }

        builder.show()
    }

    override fun onItemClick(done: Boolean, position: Int, docId: String) {
        onAlertDialog(done, position)
    }

    fun getDream(dreamCost: Int) {
        users.document(viewModel.email).get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val fundsValue = document.data?.get("funds") as Long

                    if (fundsValue - dreamCost < 0) {
                        context?.showToast("Средств не достачно")
                    } else {

                        users.document(viewModel.email)
                            .update("funds", fundsValue - dreamCost)
                        viewModel.deleteDream(getDreamDocId())
                        context?.showToast("Поздравляю, ваша мечта осуществилась")

                        val action = GoalsFragmentDirections.actionGoalsFragmentToReceivingFragment(
                            dreamName = args.dreamName,
                            dreamCost = args.dreamCost,
                            dreamImgUrl = args.imgUrl,
                            storeId = getStoredId()
                        )

                        findNavController().navigate(action)
                        isClickOpenAddGoal = true
                    }

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun getDreamDocId(): String{
        return "${args.dreamName}-${args.createDate}-${args.createTime}"
    }

    private fun getStoredId(): String{
        return "${viewModel.email}/${getDreamDocId()}"
    }


}