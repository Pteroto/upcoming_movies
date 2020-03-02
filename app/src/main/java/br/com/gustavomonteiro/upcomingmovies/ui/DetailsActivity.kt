package br.com.gustavomonteiro.upcomingmovies.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import br.com.gustavomonteiro.upcomingmovies.R
import br.com.gustavomonteiro.upcomingmovies.presentation.DetailsViewModel
import br.com.gustavomonteiro.upcomingmovies.presentation.DetailsViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setViewModel()
        setObservers()
        viewModel.loadData(intent)
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, DetailsViewModelFactory()).get(DetailsViewModel::class.java)
    }

    private fun setObservers() {
        viewModel.onLoadedData.observe(this, Observer { movie ->
            movie?.let {
                textViewTitle.text = it.name
                textViewDescription.text = it.synopsis
                textViewReleaseDate.text = it.releaseDate

                it.genres.forEach { genre ->
                    val textView = TextView(this).apply { text = genre }
                    linearLayoutGenresContainer.addView(textView)
                }

                val url = "https://image.tmdb.org/t/p/w154" + it.poster
                Glide.with(this).load(url).into(imageViewMovieCover)
            }
        })

        viewModel.onLoadError.observe(this, Observer {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialog_title)
            builder.setMessage(R.string.unknown_error_message)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}