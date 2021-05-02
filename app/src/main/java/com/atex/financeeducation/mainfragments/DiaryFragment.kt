package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.NotesAdapter
import com.atex.financeeducation.data.NoteItem
import com.atex.financeeducation.databinding.DiaryFragmentBinding
import com.atex.financeeducation.enums.Expenses
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.example.androidkeyboardstatechecker.showToast


class DiaryFragment : Fragment(R.layout.diary_fragment) {

    private val list = ArrayList<NoteItem>()
    private lateinit var adapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: DiaryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DiaryFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter(list, this)
        recyclerView = view.findViewById(R.id.notes_recycler)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager


        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
        Toast.makeText(activity, viewModel.email , Toast.LENGTH_SHORT).show()

        viewModel.getNotes().observe(viewLifecycleOwner,
            androidx.lifecycle.Observer<List<NoteItem>> { topics ->
                adapter.setNotesList(topics)
            })
        /*viewModel.getTopics(args.chapterId).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer<List<TopicItem>> { topics ->
                adapter.setTopicList(topics)
            })*/

        binding.addNoteBtn.setOnClickListener {
//            val action = BudgetFragmentDirections.actionBudgetFragmentToTransactionFragment(expense, money, procent)
            val action = DiaryFragmentDirections.actionDiaryFragmentToAddNoteFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}