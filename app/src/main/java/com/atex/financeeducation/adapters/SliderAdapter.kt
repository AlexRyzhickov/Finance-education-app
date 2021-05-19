package com.atex.financeeducation.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.atex.financeeducation.R
import com.atex.financeeducation.data.DreamItem
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class SliderAdapter(
    var context: Context?,
    private val listener: OnItemClickListener,
    options: FirestoreRecyclerOptions<DreamItem>
) : FirestoreRecyclerAdapter<DreamItem, SliderAdapter.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.slide_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        model: DreamItem
    ) {
        holder.slideDescription.setText(model.dreamName)
        val costString = model.dreamCost.toString()
        holder.cost.text = "$costString ₽"
        Glide
            .with(context!!)
            .load(model.imgUrl)
            .into(holder.slideImageView);

        holder.linkBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.link))
            startActivity(context!!, browserIntent, null)
        }

        holder.goalsBtn.setOnClickListener {
            listener.onItemClick(model.createDate, model.createTime, model.dreamName)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val img: ImageView
//        val name: TextView
        val slideImageView: ImageView
        val slideDescription: TextView
        val cost: TextView
        val linkBtn: Button
        val goalsBtn: Button

        init {
            slideImageView = itemView.findViewById<ImageView>(R.id.img)
            slideDescription = itemView.findViewById<TextView>(R.id.name)
            cost = itemView.findViewById<TextView>(R.id.cost)
            linkBtn = itemView.findViewById<Button>(R.id.linkBtn)
            goalsBtn = itemView.findViewById(R.id.goalsBtn)
//            img = itemView.findViewById<ImageView>(R.id.img)
//            name = itemView.findViewById<TextView>(R.id.name)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(createDate: String, createTime: String, dreamName: String)
    }

    /*override fun getCount(): Int {
        return listDreams.size
    }

    fun setList(dreams: List<DreamItem>){
        listDreams = dreams
        notifyDataSetChanged()
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
    }*/

}