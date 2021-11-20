package com.latihan.travelblogindonesia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.latihan.travelblogindonesia.http.Blog
import com.latihan.travelblogindonesia.http.*

class BlogDetailActivity : AppCompatActivity() {
    private var textTitle: TextView? = null
    private var textDate: TextView? = null
    private var textAuthor: TextView? = null
    private var textRating: TextView? = null
    private var textDescription: TextView? = null
    private var textViews: TextView? = null
    private var ratingBar: RatingBar? = null
    private var imageAvatar: ImageView? = null
    private var imageMain: ImageView? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blogdetail)
        imageMain = findViewById(R.id.imageMain)
        imageAvatar = findViewById(R.id.imageAvatar)
        val imageBack = findViewById<ImageView>(R.id.imageBack)
        imageBack.setOnClickListener { v: View? -> finish() }
        textTitle = findViewById(R.id.textTitle)
        textDate = findViewById(R.id.textDate)
        textAuthor = findViewById(R.id.textAuthor)
        textRating = findViewById(R.id.textRating)
        textViews = findViewById(R.id.textViews)
        textDescription = findViewById(R.id.textDescription)
        ratingBar = findViewById(R.id.ratingBar)
        progressBar = findViewById(R.id.progressBar)
        showData(intent.extras!!.getParcelable(EXTRAS_BLOG))
    }

    private fun showData(blog: Blog?) {
        progressBar!!.visibility = View.GONE
        textTitle!!.text = blog!!.title
        textDate!!.text = blog.date
        textAuthor!!.text = blog.author!!.name
        textRating!!.text = blog.rating.toString()
        textViews!!.text = String.format("(%d views)", blog.views)
        textDescription!!.text = blog.description.toString()
        ratingBar!!.rating = blog.rating
        ratingBar!!.visibility = View.VISIBLE
        Glide.with(this)
            .load(blog.imageURL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageMain!!)
        Glide.with(this)
            .load(blog.author!!.avatarURL)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageAvatar!!)
    }

    companion object {
        private const val EXTRAS_BLOG = "EXTRAS_BLOG"
        fun start(activity: Activity, blog: Blog?) {
            val intent = Intent(activity, BlogDetailActivity::class.java)
            intent.putExtra(EXTRAS_BLOG, blog)
            activity.startActivity(intent)
        }
    }
}