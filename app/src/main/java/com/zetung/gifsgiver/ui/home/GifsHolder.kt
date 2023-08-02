package com.zetung.gifsgiver.ui.home

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener


class GifsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<ImageView>(R.id.ivGif)
    private val likeButton = itemView.findViewById<CheckBox>(R.id.likeButton)

    fun bind(data: GifModel, listener: OnLikeClickListener){
        Glide.with(itemView.context).load(data.url).into(imageView)
        likeButton.isChecked = data.like
        likeButton.setOnClickListener {
            listener.onLikeClick(adapterPosition, data)
        }
    }
}