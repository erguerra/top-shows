package com.github.erguerra.topshows.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.ui.RecyclerViewItemClickListener
import com.github.erguerra.topshows.utils.formatDateToBrazilian
import com.github.erguerra.topshows.utils.load
import kotlinx.android.synthetic.main.fragment_tvshow.view.*
import org.w3c.dom.Text

class TvShowListAdapter(private val itemClickListener: RecyclerViewItemClickListener, private val itemViewHolder: Int) : PagedListAdapter<TvShow, TvShowListAdapter.ViewHolder>(tvShowDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemViewHolder, parent, false)
        return TvShowListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.itemView.setOnClickListener {
            itemClickListener.onRecyclerViewItemClick(it, position)
        }

        tvShow?.let {
            holder.title.text = it.title
            holder.firstAirDate.text = formatDateToBrazilian(it.firstAirDate)
            holder.voteAverage.rating = (it.voteAverage/2).toFloat()
            holder.poster.load(it.posterPath)
        }

        println(tvShow?.posterPath)


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.title
        val poster : ImageView = itemView.poster_image
        val voteAverage : RatingBar = itemView.vote_average
        val firstAirDate : TextView = itemView.first_air_date
    }

    companion object {
        val tvShowDiff = object : DiffUtil.ItemCallback<TvShow> () {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}