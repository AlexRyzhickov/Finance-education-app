package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.atex.financeeducation.viewmodel.CommonViewModel

class BudgetFragment : Fragment() {

    private val list = ArrayList<DreamItem>()
    private lateinit var adapter: ChapterAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: BudgetFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var authInterface: AutorizationInterface

    private lateinit var viewModel: CommonViewModel
    private lateinit var userInformation: UserInformation

    init {
        list.add(
            DreamItem(
                "Велосипед",
                15000,
                "https://avatars.mds.yandex.net/get-mpic/3927667/img_id3167248823355361820.jpeg/orig",
                "https://pokupki.market.yandex.ru/product/velosiped-altair-mtb-ht-20-1-0-20-1-sk-rost-10-5-2020-2021-temno-sinii-biriuzovyi-1bkt1j101002/101195174748?show-uid=16174587691743729226006001&offerid=GF1rGt-VrSTn3wjrq7uPgQ"
            )
        )
        list.add(
            DreamItem(
                "Apple airpods pro",
                25000,
                "https://avatars.mds.yandex.net/get-mpic/3614670/img_id1271146742177048795.jpeg/orig",
                "https://www.apple.com/ru/airpods-pro/"
            )
        )
        list.add(
            DreamItem(
                "Яндекс.Станция Мини",
                4980,
                "https://avatars.mds.yandex.net/get-mpic/1911047/img_id2701886402505123731.jpeg/x248_trim",
                "https://pokupki.market.yandex.ru/catalog/umnye-kolonki/71716/list?suggest_text=%D0%AF%D0%BD%D0%B4%D0%B5%D0%BA%D1%81%20%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D1%8F&hid=15553892&glfilter=7893318%3A15562112"
            )
        )
        list.add(
            DreamItem(
                "Артбук",
                1490,
                "https://www.1c-interes.ru/upload/resize_src/80/8025149030a513c62fd942b28ec1d15d.jpg",
                "https://www.1c-interes.ru/catalog/knigi/artbuk_iskusstvo_dishonored_2/"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BudgetFragmentBinding.inflate(inflater,container,false)

         binding.cardView.setOnClickListener {
             openTransactionFragment(Expenses.PIGGY_BANK, userInformation.funds, 25.15f)
         }

        binding.cardView2.setOnClickListener {
            openTransactionFragment(Expenses.UNTOUCHABLES,userInformation.untouchable, 25.15f)
        }

        binding.cardView3.setOnClickListener {
            openTransactionFragment(Expenses.DAILY,userInformation.daily, 25.15f)
        }

        activity?.let {
            instantiateNavigationInterface(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChapterAdapter(list, this)
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
        viewModel.getInformAboutUser().observe(viewLifecycleOwner,
            androidx.lifecycle.Observer<UserInformation> { userInform ->
                userInformation = userInform
                binding.fundsAmount.setText(userInform.funds.toString() + " р.")
                binding.untouchableAmount.setText(userInform.untouchable.toString() + " р.")
                binding.dailyAmount.setText(userInform.daily.toString() + " р.")
                binding.procent1.setText(userInform.getFundsPercentages())
                binding.procent2.setText(userInform.getUntouchablePercentages())
                binding.procent3.setText(userInform.getDailyPercentages())



//                adapter.setNotesList(topics)
            })
//        viewModel.getInformAboutUser()

        binding.goOutBtn.setOnClickListener {
            authInterface.signOut()
            val action = BudgetFragmentDirections.actionBudgetFragmentToSignInFragment()
            findNavController().navigate(action)
            authInterface.hideBottomNavView()
        }

    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        authInterface = context as AutorizationInterface
    }

    fun openTransactionFragment(expense: Expenses, money: Int, procent: Float){
        val action = BudgetFragmentDirections.actionBudgetFragmentToTransactionFragment(expense, money, procent)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}