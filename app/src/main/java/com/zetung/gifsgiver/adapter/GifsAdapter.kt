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
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.api.LocalDb
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.FavoritesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException
import kotlin.concurrent.thread


class GifsAdapter(private val context: Context,
                  var gifs: MutableList<DataObject>,
                  private val favoriteDb: FavoriteDbApi,
                  private val favoriteList: MutableList<String>) : RecyclerView.Adapter<GifsAdapter.ViewHolder>(){

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.images.gif.url).into(holder.imageView)

        holder.likeButton.isChecked = data.id in favoriteList

        holder.likeButton.setOnClickListener {
            if(holder.likeButton.isChecked){
                favoriteDb.addToFavorite(data.id,data.images.gif.url)
                favoriteList.add(data.id)
            } else {
                favoriteDb.deleteFromFavorite(data.id)
                favoriteList.remove(data.id)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<DataObject>){
        this.gifs = data
        this.notifyDataSetChanged()
    }
}