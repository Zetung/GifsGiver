package com.zetung.gifsgiver.ui

import com.zetung.gifsgiver.repository.model.FavoritesModel

interface OnLikeClickListener {
    fun onLikeClick(position: Int,data: FavoritesModel, isLiked: Boolean)
}