package com.zetung.gifsgiver

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zetung.gifsgiver.adapter.GifsAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gifView = findViewById<RecyclerView>(R.id.gifView)

        val gifs = mutableListOf<Drawable>()
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif1,null)!!)
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif2,null)!!)
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif3,null)!!)
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif1,null)!!)
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif2,null)!!)
        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif3,null)!!)

        val adapter = GifsAdapter(this, gifs)

        gifView.adapter = adapter
        gifView.layoutManager = LinearLayoutManager(this)

    }
}