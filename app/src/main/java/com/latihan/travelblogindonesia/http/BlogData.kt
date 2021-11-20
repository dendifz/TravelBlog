package com.latihan.travelblogindonesia.http

import com.latihan.travelblogindonesia.http.Blog

class BlogData {
    val data: List<Blog>? = null
        get() = field ?: ArrayList()
}
