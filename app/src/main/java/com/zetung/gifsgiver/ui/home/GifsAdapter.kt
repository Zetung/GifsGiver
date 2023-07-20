package com.zetung.gifsgiver.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener


class GifsAdapter(var gifs: MutableList<GifModel>) : RecyclerView.Adapter<GifsHolder.GifsHolder>(){

    private lateinit var likeClickListener: OnLikeClickListener
    fun setOnButtonClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsHolder.GifsHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_item, parent, false)
        return GifsHolder.GifsHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    override fun onBindViewHolder(gifHolder: GifsHolder.GifsHolder, position: Int) {
        if(gifHolder.adapterPosition != RecyclerView.NO_POSITION){
            val data = gifs[gifHolder.adapterPosition]
            gifHolder.bind(data,likeClickListener)
        }
    }

}