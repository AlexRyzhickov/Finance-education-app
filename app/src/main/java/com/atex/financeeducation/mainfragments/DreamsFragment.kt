package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.atex.financeeducation.R
import com.atex.financeeducation.adapters.SliderAdapter
import com.atex.financeeducation.data.DreamItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class DreamsFragment : Fragment(R.layout.dreams_fragment) {

    private lateinit var mSlideViewPager: ViewPager
    private lateinit var sliderAdapter: SliderAdapter
    private var list = ArrayList<DreamItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.add(DreamItem("Велосипед",15000,"https://avatars.mds.yandex.net/get-mpic/3927667/img_id3167248823355361820.jpeg/orig","https://pokupki.market.yandex.ru/product/velosiped-altair-mtb-ht-20-1-0-20-1-sk-rost-10-5-2020-2021-temno-sinii-biriuzovyi-1bkt1j101002/101195174748?show-uid=16174587691743729226006001&offerid=GF1rGt-VrSTn3wjrq7uPgQ"))
        list.add(DreamItem("Apple airpods pro",25000,"https://avatars.mds.yandex.net/get-mpic/3614670/img_id1271146742177048795.jpeg/orig","https://www.apple.com/ru/airpods-pro/"))
        list.add(DreamItem("Яндекс.Станция Мини",4980,"https://avatars.mds.yandex.net/get-mpic/1911047/img_id2701886402505123731.jpeg/x248_trim","https://pokupki.market.yandex.ru/catalog/umnye-kolonki/71716/list?suggest_text=%D0%AF%D0%BD%D0%B4%D0%B5%D0%BA%D1%81%20%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D1%8F&hid=15553892&glfilter=7893318%3A15562112"))
        list.add(DreamItem("Артбук",1490,"https://www.1c-interes.ru/upload/resize_src/80/8025149030a513c62fd942b28ec1d15d.jpg","https://www.1c-interes.ru/catalog/knigi/artbuk_iskusstvo_dishonored_2/"))


        mSlideViewPager = view.findViewById(R.id.SlideViewPager)
        sliderAdapter = SliderAdapter(context, list)
        mSlideViewPager.adapter = sliderAdapter

        view.findViewById<FloatingActionButton>(R.id.add_dream_btn).setOnClickListener {
            val action =  DreamsFragmentDirections.actionDreamsFragmentToAddDreamFragment()
            findNavController().navigate(action)
        }
    }
}