package com.mindorks.framework.mediastorecursorloader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.media_item_layout.view.*

class RvAdapter(private val data : List<MediaData>) : RecyclerView.Adapter<myHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return myHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.media_item_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.bindMedia(data[position])
    }
}

class myHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val image  = view.imageView
    private val date_added = view.textView

    fun bindMedia(tmp: MediaData) {
        Picasso.get().load(tmp.uri).into(image)
        date_added.text = tmp.date_added
    }
}