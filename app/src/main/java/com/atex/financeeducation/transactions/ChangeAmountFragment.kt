package com.atex.financeeducation.transactions

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.databinding.ChangeAmountFragmentBinding
import com.atex.financeeducation.enums.ChangeAmountState
import com.atex.financeeducation.enums.Expenses
import com.atex.financeeducation.interfaces.KeybordStateListener

class ChangeAmountFragment : Fragment(), KeybordStateListener {

    private var _binding: ChangeAmountFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ChangeAmountFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChangeAmountFragmentBinding.inflate(inflater, container, false)

//        val expense = args.expenseName

//        textField = rootView.findViewById(R.id.textField)
        binding.textField.setStartIconTintList(null)

        when(args.changeState){
            ChangeAmountState.ADD ->{
                binding.commitBtn.text = "Зачислить"
                binding.titleChangeAmount.text = "Введите сумму для зачисления"
            }
            ChangeAmountState.REDUCE->{
                binding.commitBtn.text = "Списать"
                binding.titleChangeAmount.text = "Введите сумму для списания"
            }
            ChangeAmountState.SET->{
                binding.commitBtn.text = "Установить"
                binding.titleChangeAmount.text = "Введите сумму для установки"
            }
        }

        when(args.expence){
            Expenses.PIGGY_BANK -> {
                binding.changeAmountImg.setImageResource(R.drawable.piggy_bank)
            }
            Expenses.UNTOUCHABLES->{
                binding.changeAmountImg.setImageResource(R.drawable.safe_box)
            }
            Expenses.DAILY->{
                binding.changeAmountImg.setImageResource(R.drawable.wallet)
            }
        }

        binding.changeAmountEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s : CharSequence?, start: Int, end: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.changeAmountEditText.setOnFocusChangeListener { view, b ->
            if (b) {
                if (binding.changeAmountEditText.text.toString()[0]=='0'){
                    binding.changeAmountEditText.setText("")
                }
            }
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

    override fun open() {
        Toast.makeText(context,"Keyboard Open", Toast.LENGTH_SHORT).show()
    }

    override fun hide() {
        Toast.makeText(context,"Keyboard closed",Toast.LENGTH_SHORT).show()
    }

}