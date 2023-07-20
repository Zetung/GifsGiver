package com.zetung.gifsgiver.ui.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener

class FavoriteAdapter(var gifs: MutableList<GifModel>) :
    RecyclerView.Adapter<FavoriteHolder.FavoriteHolder>(){

    private lateinit var likeClickListener: OnLikeClickListener

    fun setOnButtonClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder.FavoriteHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        return FavoriteHolder.FavoriteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  gifs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(favHolder: FavoriteHolder.FavoriteHolder, position: Int) {
        if(favHolder.adapterPosition != RecyclerView.NO_POSITION){
            val data = gifs[favHolder.adapterPosition]
            favHolder.bind(data,likeClickListener)
        }
    }

}
