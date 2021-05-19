package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.ChapterAdapter
import com.atex.financeeducation.data.*
import com.atex.financeeducation.databinding.BudgetFragmentBinding
import com.atex.financeeducation.enums.Expenses
import com.atex.financeeducation.interfaces.AutorizationInterface
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BudgetFragment : Fragment() {

    //    private val list = ArrayList<DreamItem>()
    private lateinit var adapter: ChapterAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: BudgetFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var authInterface: AutorizationInterface
    private lateinit var changeBotNavViewInterface: ChangeBottomNavView

    private lateinit var viewModel: CommonViewModel
    private lateinit var userInformation: UserInformation

    private val db = Firebase.firestore
    private val users = db.collection("Users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BudgetFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        val query: Query = users.document(viewModel.email).collection("dreams")
        val options = FirestoreRecyclerOptions.Builder<DreamItem>()
            .setQuery(query, DreamItem::class.java)
            .build()

        adapter = ChapterAdapter(this, options)
        recyclerView = binding.recycler
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        binding.cardView.setOnClickListener {
            openTransactionFragment(
                Expenses.PIGGY_BANK,
                userInformation.funds.toString(),
                userInformation.getFundsPercentages()
            )
        }

        binding.cardView2.setOnClickListener {
            openTransactionFragment(
                Expenses.UNTOUCHABLES,
                userInformation.untouchable.toString(),
                userInformation.getUntouchablePercentages()
            )
        }

        binding.cardView3.setOnClickListener {
            openTransactionFragment(
                Expenses.DAILY,
                userInformation.daily.toString(),
                userInformation.getDailyPercentages()
            )
        }

        activity?.let {
            instantiateNavigationInterface(it)
        }

        binding.goOutBtn.setOnClickListener {
            authInterface.signOut()
            val action = BudgetFragmentDirections.actionBudgetFragmentToSignInFragment()
            findNavController().navigate(action)
            changeBotNavViewInterface.hideBottomNavView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users.document(viewModel.email)
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
                val userInform: UserInformation? = snapshot?.toObject(UserInformation::class.java)
                userInform?.let {
                    userInformation = it
                    view.findViewById<TextView>(R.id.funds_amount).text =
                        it.funds.toString() + " р."
                    view.findViewById<TextView>(R.id.untouchable_amount).text =
                        it.untouchable.toString() + " р."
                    view.findViewById<TextView>(R.id.daily_amount).text =
                        it.daily.toString() + " р."
                    view.findViewById<TextView>(R.id.funds_procent).text = it.getFundsPercentages()
                    view.findViewById<TextView>(R.id.untouchable_procent).text =
                        it.getUntouchablePercentages()
                    view.findViewById<TextView>(R.id.daily_procent).text = it.getDailyPercentages()
//                binding.fundsAmount.setText(it.funds.toString() + " р.")
//                binding.untouchableAmount.setText(it.untouchable.toString() + " р.")
//                binding.dailyAmount.setText(it.daily.toString() + " р.")
//                binding.procent1.setText(it.getFundsPercentages())
//                binding.procent2.setText(it.getUntouchablePercentages())
//                binding.procent3.setText(it.getDailyPercentages())
                }

            }
    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        authInterface = context as AutorizationInterface
        changeBotNavViewInterface = context as ChangeBottomNavView
    }

    fun openTransactionFragment(expense: Expenses, money: String, procent: String) {
        val action = BudgetFragmentDirections.actionBudgetFragmentToTransactionFragment(
            expense,
            money,
            procent
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}