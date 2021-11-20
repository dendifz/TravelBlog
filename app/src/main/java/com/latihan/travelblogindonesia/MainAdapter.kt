package com.latihan.travelblogindonesia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.latihan.travelblogindonesia.http.Blog
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<Blog, MainAdapter.MainViewHolder?>(DIFF_CALLBACK) {

    fun interface OnItemClickListener {
        fun onItemClicked(blog: Blog?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_list, parent, false)
        return MainViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class MainViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView
        private val textDate: TextView
        private val imageAvatar: ImageView
        private var blog: Blog? = null
        fun bindTo(blog: Blog?) {
            this.blog = blog
            textTitle.text = blog!!.title
            textDate.text = blog.date
            Glide.with(itemView)
                .load(blog.author!!.avatarURL)
                .transform(CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar)
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClicked(
                    blog
                )
            }
            textTitle = itemView.findViewById(R.id.textTitle)
            textDate = itemView.findViewById(R.id.textDate)
            imageAvatar = itemView.findViewById(R.id.imageAvatar)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Blog> =
            object : DiffUtil.ItemCallback<Blog>() {
                override fun areItemsTheSame(
                    oldData: Blog,
                    newData: Blog
                ): Boolean {
                    return oldData.id == newData.id
                }

                override fun areContentsTheSame(
                    oldData: Blog,
                    newData: Blog
                ): Boolean {
                    return oldData.equals(newData)
                }
            }
    }

    fun sortByTitle() {
        val currentList: List<Blog> = ArrayList(currentList) // 1
        Collections.sort(
            currentList
        ) { o1, o2 -> o1.title!!.compareTo(o2.title.toString()) } // 2
        submitList(currentList) // 3
    }

    private var originalList: List<Blog> = ArrayList()

    fun setData(@Nullable list: List<Blog>) {
        originalList = list
        super.submitList(list)
    }

    fun filter(query: String) {
        val filteredList: MutableList<Blog> = ArrayList()
        for (blog in originalList) { // 1
            if (blog.title!!.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                filteredList.add(blog)
            }
        }
        submitList(filteredList)
    }
}