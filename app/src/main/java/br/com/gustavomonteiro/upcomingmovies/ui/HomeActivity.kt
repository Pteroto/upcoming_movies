package br.com.gustavomonteiro.upcomingmovies.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.gustavomonteiro.upcomingmovies.R
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.presentation.DetailsViewModel.Companion.KEY_MOVIE_OBJECT
import br.com.gustavomonteiro.upcomingmovies.presentation.HomeViewModel
import br.com.gustavomonteiro.upcomingmovies.presentation.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var mSearchItem: MenuItem
    private lateinit var viewModel: HomeViewModel
    private lateinit var movieList: List<Movie>
    private lateinit var adapter: MoviesAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        setViewModel()
        setList()
        setObservers()
    }

    private fun setViewModel() {
        viewModel =
            ViewModelProviders.of(this, HomeViewModelFactory(this)).get(HomeViewModel::class.java)
    }

    private fun setObservers() {
        val activity = this

        viewModel.movieList.observe(this, Observer { movie ->
            movie?.let {
                movieList = it
                adapter.updateData(movieList)

                Log.d("teste", "updated")

                if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
            }
        })

        viewModel.onClickOnMovieOnList.observe(this, Observer { id ->
            id?.let {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(KEY_MOVIE_OBJECT, movieList[it])
                startActivity(intent)
            }
        })

        viewModel.onErrorMessageId.observe(this, Observer {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialog_title)
            builder.setMessage(it)
            builder.create().show()
        })

        viewModel.onErrorMessageString.observe(this, Observer {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialog_title)
            builder.setMessage(it)
            builder.create().show()
        })
    }

    private fun setList() {
        adapter = MoviesAdapter(this) { viewModel.onClickListItem(it) }
        viewModel.getMovies()

        recyclerViewMovieList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewMovieList.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            viewModel.getMovies()
            searchView.setQuery("", false)
            searchView.clearFocus()
            searchView.isIconified = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        mSearchItem = menu.findItem(R.id.searchView)
        searchView = mSearchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null || query == "") {
                    return false
                }

                viewModel.onSearchedMovies(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return if (query?.isNotEmpty() == true) {
                    viewModel.onSearchedMovies(query)
                    true
                } else {
                    viewModel.getMovies()
                    true
                }
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchView -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
