package com.atex.financeeducation.transactions

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.databinding.TransactionFragmentBinding
import com.atex.financeeducation.enums.ChangeAmountState
import com.atex.financeeducation.enums.Expenses
import com.atex.financeeducation.mainfragments.BudgetFragmentDirections

class TransactionFragment : Fragment() {

    private var _binding: TransactionFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: TransactionFragmentArgs by navArgs()
    private  lateinit var expenses: Expenses

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TransactionFragmentBinding.inflate(inflater, container, false)

        when(args.expense){
            Expenses.UNTOUCHABLES -> {
                Toast.makeText(context,"UNTOUCHABLES",Toast.LENGTH_SHORT).show()
                binding.transactionTitle.text = "Неприкосновенные"
                binding.transactionImg.setImageResource(R.drawable.safe_box)
                binding.writeOffCard.visibility = View.GONE
//                binding.addCard.visibility = View.GONE
                binding.transferCard.visibility = View.GONE
            }
            Expenses.DAILY -> {
                Toast.makeText(context,"DAILY",Toast.LENGTH_SHORT).show()
                binding.transactionTitle.text = "Повседневные"
                binding.transactionImg.setImageResource(R.drawable.wallet)
            }
            Expenses.PIGGY_BANK -> {
                Toast.makeText(context,"PIGGY_BANK",Toast.LENGTH_SHORT).show()
                binding.transactionTitle.text = "Копилка"
                binding.transactionImg.setImageResource(R.drawable.piggy_bank)
            }
        }

        val  money = args.money
        val procent = args.procent
        expenses = args.expense

        binding.moneyOnAccount.text = "Сумма ₽ " + money
        binding.procent.text = procent

        binding.addCard.setOnClickListener {
            val action = TransactionFragmentDirections.actionTransactionFragmentToChangeAmountFragment(ChangeAmountState.ADD,args.expense)
            findNavController().navigate(action)
        }

        binding.writeOffCard.setOnClickListener {
            val action = TransactionFragmentDirections.actionTransactionFragmentToChangeAmountFragment(ChangeAmountState.REDUCE,args.expense)
            findNavController().navigate(action)
        }

        binding.setAmountCard.setOnClickListener {
            val action = TransactionFragmentDirections.actionTransactionFragmentToChangeAmountFragment(ChangeAmountState.SET,args.expense)
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}