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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.data.DreamItem
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentReference


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
            listener.onItemClick(model.createDate, model.createTime, model.dreamName, model.dreamCost, model.imgUrl)
        }

    }

    fun getItemRef(position: Int): DocumentReference {
        return getSnapshots().getSnapshot(position).getReference()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val slideImageView: ImageView
        val slideDescription: TextView
        val cost: TextView
        val linkBtn: Button
        val goalsBtn: Button

        init {
            slideImageView = itemView.findViewById<ImageView>(R.id.receiving_dream_img)
            slideDescription = itemView.findViewById<TextView>(R.id.receiving_dream_name)
            cost = itemView.findViewById<TextView>(R.id.receiving_dream_cost)
            linkBtn = itemView.findViewById<Button>(R.id.linkBtn)
            goalsBtn = itemView.findViewById(R.id.goalsBtn)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(createDate: String, createTime: String, dreamName: String, dreamCost: Int, imgUrl: String)
    }

}