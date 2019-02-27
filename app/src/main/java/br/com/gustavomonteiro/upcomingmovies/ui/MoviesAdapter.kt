package br.com.gustavomonteiro.upcomingmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.gustavomonteiro.upcomingmovies.R
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MoviesAdapter(
    private val context: Context, private val movieList: MutableList<Movie>,
    private val listener: (id: Int) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    constructor(context: Context, listener: (id: Int) -> Unit) : this(context , mutableListOf(), listener)

    fun updateData(list: List<Movie>) {
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(movieList[position], listener, position)
    }

    class MovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private val title = itemView.textViewTitle
        private val releaseDate = itemView.textViewReleaseDate
        private val poster = itemView.imageViewPoster

        fun bind(movie: Movie, listener: (id: Int) -> Unit, position: Int) {
            itemView.setOnClickListener { listener(position) }
            title.text = movie.name
            releaseDate.text = movie.releaseDate

            val url = "https://image.tmdb.org/t/p/w92" + movie.poster
            Glide.with(itemView).load(url).into(poster)
        }
    }
}