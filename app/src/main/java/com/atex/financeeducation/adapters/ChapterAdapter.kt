package com.atex.financeeducation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atex.financeeducation.R
import com.atex.financeeducation.data.DreamItem
import com.atex.financeeducation.mainfragments.BudgetFragment
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class ChapterAdapter(
    private val context: BudgetFragment, options: FirestoreRecyclerOptions<DreamItem>
) :
    FirestoreRecyclerAdapter<DreamItem, ChapterAdapter.ViewHolder>(options)
    /*RecyclerView.Adapter<ChapterAdapter.ViewHolder>()*/ {

//    private var dreamList: List<DreamItem> = ArrayList<DreamItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card,
            parent, false
        )
        return ViewHolder(itemView)
    }

   /* override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dreamList[position]
        if (currentItem.dreamName.length <= 15) {
            holder.name.text = currentItem.dreamName
        }else{
            holder.name.text = currentItem.dreamName.substring(0,12) + ".."
        }
        Glide
            .with(context)
            .load(currentItem.imgUrl)
            .into(holder.img);

    }*/

 /*   override fun getItemCount(): Int {
        return dreamList.size
    }*/

/*    fun setList(dreams: List<DreamItem>){
        dreamList = dreams
        notifyDataSetChanged()
    }*/

//    fun setChapterList(list: List<ChapterItem>) {
//        dreamList = list
//        notifyDataSetChanged()
//    }

//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        val img: ImageView = itemView.chapter_img
//        val chapterName: TextView = itemView.chapter_name
//
//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(dreamList[position].chapterId)
//            }
//        }
//    }

//    interface OnItemClickListener : RecyclerView.ViewHolder {
//        fun onItemClick(chapterId: String)
//    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var name: TextView
        init {
            img = itemView.findViewById<ImageView>(R.id.img)
            name = itemView.findViewById<TextView>(R.id.name)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: DreamItem) {
        if (model.dreamName.length <= 15) {
            holder.name.text = model.dreamName
        }else{
            holder.name.text = model.dreamName.substring(0,12) + ".."
        }
        Glide
            .with(context)
            .load(model.imgUrl)
            .into(holder.img);
    }
}