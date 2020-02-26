package com.github.erguerra.topshows.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.ui.RecyclerViewItemClickListener
import com.github.erguerra.topshows.ui.adapters.TvShowListAdapter.Companion.tvShowDiff //Uses same Diff cause it's the same object type
import com.github.erguerra.topshows.utils.load
import kotlinx.android.synthetic.main.related_tv_show.view.*

class RelatedTvShowsListAdapter (private val itemClickListener: RecyclerViewItemClickListener,
                                 private val itemViewHolder:  Int) :  PagedListAdapter<TvShow, RelatedTvShowsListAdapter.ViewHolder>(tvShowDiff){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemViewHolder, parent, false)
        return RelatedTvShowsListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relatedTvShow = getItem(position)
        holder.itemView.setOnClickListener {
            itemClickListener.onRecyclerViewItemClick(it, position)
        }

        relatedTvShow?.let{
            holder.poster.load(it.posterPath)
        }
    }




    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poster: ImageView = itemView.poster_image
    }

}