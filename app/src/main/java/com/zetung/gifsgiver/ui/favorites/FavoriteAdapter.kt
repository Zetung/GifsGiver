package com.zetung.gifsgiver.ui.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zetung.gifsgiver.R
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.repository.model.FavoritesModel
import com.zetung.gifsgiver.ui.OnLikeClickListener

class FavoriteAdapter(private val context: Context,
                      var gifs: MutableList<FavoritesModel>,
                      //private val favoriteDb: FavoriteDbApi
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private lateinit var likeClickListener: OnLikeClickListener

    fun setOnButtonClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.ivGif)
        val likeButton = itemView.findViewById<CheckBox>(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //return gifsLiveData.value?.size ?: 0
        return  gifs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val gifs = gifsLiveData.value
//        gifs?.get(position)?.let { data ->
//            Glide.with(context).load(data.url).into(holder.imageView)
//
//            holder.likeButton.setOnClickListener {
//                //favoriteDb.deleteFromFavorite(data.id)
//                //gifs.remove(data)
//                this.notifyItemRemoved(holder.adapterPosition)
//            }
//        }

        if(holder.adapterPosition != RecyclerView.NO_POSITION){
            val data = gifs[holder.adapterPosition]
            Glide.with(context).load(data.url).into(holder.imageView)

            holder.likeButton.setOnClickListener {
                //favoriteDb.deleteFromFavorite(data.id)
                likeClickListener.onLikeClick(holder.adapterPosition,data, true)

//                gifs.remove(data)
//                this.notifyItemRemoved(holder.adapterPosition)
            }
        }
    }

}
