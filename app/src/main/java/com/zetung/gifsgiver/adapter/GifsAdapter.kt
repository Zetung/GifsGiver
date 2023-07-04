package com.zetung.gifsgiver.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.model.Gif
import com.zetung.gifsgiver.databinding.GifItemBinding


class GifsAdapter(val context: Context,var gifs: List<Gif>) : RecyclerView.Adapter<GifsAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.ivGif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.url).into(holder.imageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Gif>){
        this.gifs = data
        this.notifyDataSetChanged()
    }
}