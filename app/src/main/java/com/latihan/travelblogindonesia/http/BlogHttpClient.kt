package com.latihan.travelblogindonesia.http

import android.util.Log
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;



class BlogHttpClient private constructor() {
    private val executor: Executor
    private val client: OkHttpClient
    private val gson: Gson

    fun loadBlogArticles(callback: BlogArticlesCallback) {
        val request: Request = Request.Builder()
            .get()
            .url(BLOG_ARTICLES_URL)
            .build()
        executor.execute {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body
                if (responseBody != null) {
                    val json = responseBody.string()
                    val blogData = gson.fromJson(json, BlogData::class.java)
                    if (blogData != null) {
                        callback.onSuccess(blogData.data)
                        return@execute
                    }
                }
            } catch (e: IOException) {
                Log.e("BlogHttpClient", "Error loading blog articles", e)
            }
            callback.onError()
        }
    }

    companion object {
        val INSTANCE = BlogHttpClient()
        public const val BASE_URL =
            "https://librakunci.me"
        public const val PATH =
            "/travelblog"
        private const val BLOG_ARTICLES_URL =
            "$BASE_URL$PATH/article.json"
    }

    init {
        executor = Executors.newFixedThreadPool(4)
        client = OkHttpClient()
        gson = Gson()
    }
}
