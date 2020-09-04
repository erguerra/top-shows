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
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.utils.formatDateToBrazilian
import com.github.erguerra.topshows.utils.load
import kotlinx.android.synthetic.main.item_tvshow.view.*

class TvShowListAdapter(private val itemViewHolder: Int) : PagedListAdapter<TvShow, TvShowListAdapter.ViewHolder>(tvShowDiff) {

    private var listener: TvShow?.() -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemViewHolder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.itemView.setOnClickListener {
            listener(tvShow)
        }

        tvShow?.let {
            holder.title.text = it.title
            holder.firstAirDate.text = formatDateToBrazilian(it.firstAirDate)
            holder.voteAverage.rating = (it.voteAverage/2).toFloat()
            holder.poster.load(it.posterPath)
        }

    }

    fun onItemClickListener(listener: TvShow?.() -> Unit){
        this.listener = listener
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