package com.zetung.gifsgiver.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener

class FavoriteAdapter(var gifs: MutableList<GifModel>) :
    RecyclerView.Adapter<FavoriteHolder>(){

    private lateinit var likeClickListener: OnLikeClickListener

    fun setOnButtonClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        return FavoriteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  gifs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(favHolder: FavoriteHolder, position: Int) {
        if(favHolder.adapterPosition != RecyclerView.NO_POSITION){
            val data = gifs[favHolder.adapterPosition]
            favHolder.bind(data,likeClickListener)
        }
    }

}
