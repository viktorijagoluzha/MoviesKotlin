package com.example.movieskotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var popularTVShow: RecyclerView
    private lateinit var popularTVShowAdapter: ShowAdapter
    private lateinit var popularTVShowLayoutMgr: LinearLayoutManager

    private var popularTVShowPage = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search= findViewById<SearchView>(R.id.searchView)


        popularTVShowAdapter = ShowAdapter(mutableListOf()) { show -> showTVShowDetails(show) }


        popularTVShow = findViewById(R.id.popular_shows)
        popularTVShowLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularTVShow.layoutManager = popularTVShowLayoutMgr
        popularTVShowAdapter = ShowAdapter(mutableListOf()) { show -> showTVShowDetails(show) }

        popularTVShow.adapter = popularTVShowAdapter
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
               search.clearFocus()
                if(popularTVShow.contains(p0)){
                    popularTVShowAdapter.filter.filter(p0)
                }else{
                    Toast.makeText(applicationContext, "Item not found", Toast.LENGTH_LONG)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                popularTVShowAdapter.filter.filter(p0)
                return false
            }
        })

        getPopularTVShows()

    }
    private fun attachPopularTVShowsOnScrollListener() {
        popularTVShow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularTVShowLayoutMgr.itemCount
                val visibleItemCount = popularTVShowLayoutMgr.childCount
                val firstVisibleItem = popularTVShowLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularTVShow.removeOnScrollListener(this)
                    popularTVShowPage++
                    getPopularTVShows()
                }
            }
        }) }

    private fun getPopularTVShows() {
        ShowsRepository.getPopularTVShows(
            popularTVShowPage,
            ::onPopularTVShowFetched,
            ::onError
        )
    }

    private fun onPopularTVShowFetched(shows: List<TVShow>) {
        popularTVShowAdapter.updateShow(shows)
        attachPopularTVShowsOnScrollListener()
    }

    private fun onError() {
        Log.WARN
    }
    private fun showTVShowDetails(show: TVShow) {
        val intent = Intent(this, DetailsActivity::class.java)

        intent.putExtra(SHOW_POSTER, show.posterPath)
        intent.putExtra(SHOW_TITLE, show.title)
        intent.putExtra(SHOW_RATING, show.rating)
        intent.putExtra(SHOW_RELEASE_DATE, show.releaseDate)
        intent.putExtra(SHOW_OVERVIEW, show.overview)
        startActivity(intent)
    }


}


