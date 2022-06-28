package com.example.movieskotlin


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ShowAdapter(
    private var shows: MutableList<TVShow>,
    private val onShowClick: (show: TVShow) -> Unit
) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {


    fun appendShow(shows: List<TVShow>) {
        this.shows.addAll(shows)
        notifyItemRangeInserted(
            this.shows.size,
            shows.size - 1
        )

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_show, parent, false)
        return ShowViewHolder(view)
    }

    override fun getItemCount(): Int = shows.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    fun updateShow(show: List<TVShow>) {
        this.shows = shows as MutableList<TVShow>
        notifyDataSetChanged()
    }


    inner class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_show_poster)

        fun bind(show: TVShow) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w780${show.posterPath}")
                .into(poster)
            itemView.setOnClickListener { onShowClick.invoke(show) }
        }
    }

}
