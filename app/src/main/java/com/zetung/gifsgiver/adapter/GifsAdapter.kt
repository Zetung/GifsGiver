package com.zetung.gifsgiver.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.model.DataGif
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.Gif


class GifsAdapter(val context: Context,var gifs: MutableList<DataObject>) : RecyclerView.Adapter<GifsAdapter.ViewHolder>(){


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.ivGif)
        val likeButton = itemView.findViewById<CheckBox>(R.id.likeButton)
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

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.images.gif.url).into(holder.imageView)

        val sharedPreferences = context.getSharedPreferences("like_prefs", Context.MODE_PRIVATE)
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        for (record in localStorage){
            if(data.id == record.key)
                holder.likeButton.isChecked = true
        }

        holder.likeButton.setOnClickListener {
            if(holder.likeButton.isChecked){
                val editor = sharedPreferences.edit()
                editor.putString(data.id, data.images.gif.url)
                editor.apply()
            } else {
                val editor = sharedPreferences.edit()
                editor.remove(data.id)
                editor.apply()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<DataObject>){
        this.gifs = data
        this.notifyDataSetChanged()
    }
}