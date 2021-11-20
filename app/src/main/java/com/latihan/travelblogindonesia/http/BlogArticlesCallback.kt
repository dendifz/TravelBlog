package com.latihan.travelblogindonesia.http


interface BlogArticlesCallback {
    fun onSuccess(blogList: List<Blog?>?)
    fun onError()
}
