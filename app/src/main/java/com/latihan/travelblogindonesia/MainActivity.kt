package com.latihan.travelblogindonesia

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.latihan.travelblogindonesia.http.Blog
import com.latihan.travelblogindonesia.http.BlogArticlesCallback
import com.latihan.travelblogindonesia.http.BlogHttpClient


class MainActivity : AppCompatActivity() {

    private val SORT_TITLE = 1
    private val SORT_DEF = 0
    private var currentSort = SORT_DEF
    private var adapter: MainAdapter? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.sort) {
                onSortClicked()
            }
            false
        }

        val searchItem = toolbar.menu.findItem(R.id.search)

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter(newText!!)
                return true
            }
        })

        adapter = MainAdapter { blog: Blog? ->
                BlogDetailActivity.start(
                    this,
                    blog
                )
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        refreshLayout = findViewById(R.id.refresh)
        refreshLayout?.setOnRefreshListener(this::loadData)

        loadData()
    }

    private fun loadData() {
        refreshLayout!!.isRefreshing = true
        BlogHttpClient.INSTANCE.loadBlogArticles(object : BlogArticlesCallback {
            override fun onSuccess(blogList: List<Blog?>?) {
                runOnUiThread {
                    refreshLayout!!.isRefreshing = false
                    adapter!!.setData(blogList as List<Blog>)
                }
            }

            override fun onError() {
                runOnUiThread {
                    refreshLayout!!.isRefreshing = false
                    showErrorSnackbar()
                }
            }
        })
    }

    private fun showErrorSnackbar() {
        val rootView: View = findViewById(R.id.content)
        val snackbar = Snackbar.make(
            rootView,
            "Error during loading blog articles",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setActionTextColor(resources.getColor(R.color.orange500))
        snackbar.setAction("Retry") {
            loadData()
            snackbar.dismiss()
        }
        snackbar.show()
    }

    private fun onSortClicked() {
        val items = arrayOf("Default" ,"Title")
        MaterialAlertDialogBuilder(this)
            .setTitle("Sort order")
            .setSingleChoiceItems(items, currentSort) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                currentSort = which
                sortData()
            }.show()
    }

    private fun sortData() {
        if (currentSort == SORT_TITLE) {
            adapter!!.sortByTitle()
        } else if (currentSort == SORT_DEF) {
            loadData()
        }
    }
}