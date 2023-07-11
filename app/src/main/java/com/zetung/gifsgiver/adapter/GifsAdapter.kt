package com.zetung.gifsgiver.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
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
import com.zetung.gifsgiver.api.LocalDb
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.FavoritesModel
import java.sql.SQLException
import kotlin.concurrent.thread


class GifsAdapter(val context: Context,var gifs: List<DataObject>) : RecyclerView.Adapter<GifsAdapter.ViewHolder>(){

    private lateinit var checkFavorites:MutableList<String>
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

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.images.gif.url).into(holder.imageView)
        loadFavorites {
            if(it && (data.id in checkFavorites))
                holder.likeButton.isChecked = true
        }

        holder.likeButton.setOnClickListener {
            if(holder.likeButton.isChecked){
                thread {
                    LocalDb.getDb(context).getFavoritesDAO()
                        .addToFavorite(FavoritesModel(data.id,data.images.gif.url))
                }
            } else {
                thread {
                    LocalDb.getDb(context).getFavoritesDAO()
                        .deleteFromFavorites(data.id)
                }
            }
        }
    }

    private fun loadFavorites(callback:(Boolean)->Unit){
        thread {
            try {
                checkFavorites = LocalDb.getDb(context).getFavoritesDAO().getAllID()
                Handler(Looper.getMainLooper()).post{
                    callback.invoke(true)
                }
            } catch (e: SQLException){
                Handler(Looper.getMainLooper()).post{
                    callback.invoke(false)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataObject>){
        this.gifs = data
        this.notifyDataSetChanged()
    }
}