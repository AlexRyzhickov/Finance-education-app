package com.atex.financeeducation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.data.GoalItem
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class GoalsAdapter(
    options: FirestoreRecyclerOptions<GoalItem>
) :
    FirestoreRecyclerAdapter<GoalItem, GoalsAdapter.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.goal_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        var date: TextView
        var text: TextView
        var status: ImageView
        init {
            date = itemView.findViewById<TextView>(R.id.goal_date)
            text = itemView.findViewById<TextView>(R.id.goal_text)
            status = itemView.findViewById<ImageView>(R.id.goal_status)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: GoalItem) {
        holder.text.text = model.text
        holder.date.text = model.date
        if (model.done){
            holder.status.setImageResource(R.drawable.green_circle)
        }else{
            holder.status.setImageResource(R.drawable.red_circle)
        }
    }
}