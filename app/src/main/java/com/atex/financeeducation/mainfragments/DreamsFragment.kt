package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.SliderAdapter
import com.atex.financeeducation.data.DreamItem
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.example.androidkeyboardstatechecker.showToast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DreamsFragment : Fragment(R.layout.dreams_fragment), SliderAdapter.OnItemClickListener {

    private lateinit var mSlideViewPager: ViewPager2
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var viewModel: CommonViewModel

    private val db = Firebase.firestore
    private val users = db.collection("Users")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        val query: Query = users.document(viewModel.email).collection("dreams")
        val options = FirestoreRecyclerOptions.Builder<DreamItem>()
            .setQuery(query, DreamItem::class.java)
            .build()


        mSlideViewPager = view.findViewById(R.id.SlideViewPager)
        sliderAdapter = SliderAdapter(context, this, options)
        mSlideViewPager.adapter = sliderAdapter

        view.findViewById<FloatingActionButton>(R.id.add_dream_btn).setOnClickListener {
            val action = DreamsFragmentDirections.actionDreamsFragmentToAddDreamFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        sliderAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        sliderAdapter.stopListening()
    }

    override fun onItemClick(createDate: String, createTime: String, dreamName: String, dreamCost: Int, imgUrl: String) {
        val action = DreamsFragmentDirections.actionDreamsFragmentToGoalsFragment(
            createDate = createDate,
            createTime = createTime,
            dreamName = dreamName,
            dreamCost = dreamCost,
            imgUrl = imgUrl
        )
        findNavController().navigate(action)
    }
}