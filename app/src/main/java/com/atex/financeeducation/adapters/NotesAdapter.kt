package com.atex.financeeducation.adapters

import android.graphics.text.LineBreaker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.data.NoteItem
import com.atex.financeeducation.mainfragments.DiaryFragment


class NotesAdapter(
    private var noteList: List<NoteItem>,
    private val context: DiaryFragment
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.date.text = "Запись от " + currentItem.date
        holder.note_1.text = currentItem.note_1
        holder.note_2.text = currentItem.note_2
        holder.note_3.text = currentItem.note_3
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            holder.note_1.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            holder.note_2.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            holder.note_3.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }


//        if (currentItem.dreamName.length <= 15) {
//            holder.name.text = currentItem.dreamName
//        }else{
//            holder.name.text = currentItem.dreamName.substring(0,12) + ".."
//        }
//        Glide
//            .with(context)
//            .load(currentItem.imgUrl)
//            .into(holder.img);

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setNotesList(list: List<NoteItem>) {
        noteList = list
        notifyDataSetChanged()
    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        var date: TextView
        var note_1: TextView
        var note_2: TextView
        var note_3: TextView
        init {
            date = itemView.findViewById<TextView>(R.id.note_date)
            note_1 = itemView.findViewById<TextView>(R.id.note_1)
            note_2 = itemView.findViewById<TextView>(R.id.note_2)
            note_3 = itemView.findViewById<TextView>(R.id.note_3)
        }
    }
}