package com.atex.financeeducation.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.atex.financeeducation.R
import com.atex.financeeducation.data.DreamItem
import com.bumptech.glide.Glide


class SliderAdapter(
    var context: Context?,
    var listDreams: List<DreamItem>
) : PagerAdapter() {

    override fun getCount(): Int {
        return listDreams.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val layoutInflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slide_layout, container, false)

        val slideImageView = view.findViewById<ImageView>(R.id.img)
        val slideDescription = view.findViewById<TextView>(R.id.name)
        val cost = view.findViewById<TextView>(R.id.cost)
        val linkBtn = view.findViewById<TextView>(R.id.linkBtn)

        slideDescription.setText(listDreams[position].dreamName)
        val costString = listDreams[position].dreamCost.toString()
        cost.text = "$costString ₽"
        Glide
            .with(context!!)
            .load(listDreams[position].imgUrl)
            .into(slideImageView);

        linkBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(listDreams[position].link))
            startActivity(context!!,browserIntent,null)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}