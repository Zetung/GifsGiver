package com.zetung.gifsgiver.ui

import com.zetung.gifsgiver.repository.model.GifModel

interface OnLikeClickListener {
    fun onLikeClick(position: Int, data: GifModel)
}