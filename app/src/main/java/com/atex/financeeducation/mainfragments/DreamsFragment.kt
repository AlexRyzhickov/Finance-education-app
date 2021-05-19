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

    //    private var list = ArrayList<DreamItem>()
    private lateinit var viewModel: CommonViewModel

    private val db = Firebase.firestore
    private val users = db.collection("Users")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        list.add(DreamItem("Велосипед",15000,"https://avatars.mds.yandex.net/get-mpic/3927667/img_id3167248823355361820.jpeg/orig","https://pokupki.market.yandex.ru/product/velosiped-altair-mtb-ht-20-1-0-20-1-sk-rost-10-5-2020-2021-temno-sinii-biriuzovyi-1bkt1j101002/101195174748?show-uid=16174587691743729226006001&offerid=GF1rGt-VrSTn3wjrq7uPgQ"))
//        list.add(DreamItem("Apple airpods pro",25000,"https://avatars.mds.yandex.net/get-mpic/3614670/img_id1271146742177048795.jpeg/orig","https://www.apple.com/ru/airpods-pro/"))
//        list.add(DreamItem("Яндекс.Станция Мини",4980,"https://avatars.mds.yandex.net/get-mpic/1911047/img_id2701886402505123731.jpeg/x248_trim","https://pokupki.market.yandex.ru/catalog/umnye-kolonki/71716/list?suggest_text=%D0%AF%D0%BD%D0%B4%D0%B5%D0%BA%D1%81%20%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D1%8F&hid=15553892&glfilter=7893318%3A15562112"))
//        list.add(DreamItem("Артбук",1490,"https://www.1c-interes.ru/upload/resize_src/80/8025149030a513c62fd942b28ec1d15d.jpg","https://www.1c-interes.ru/catalog/knigi/artbuk_iskusstvo_dishonored_2/"))

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

    override fun onItemClick(createDate: String, createTime: String, dreamName: String) {
//        context?.showToast(position.toString())
        val action = DreamsFragmentDirections.actionDreamsFragmentToGoalsFragment(
            createDate = createDate,
            createTime = createTime,
            dreamName = dreamName
        )
        findNavController().navigate(action)
    }
}